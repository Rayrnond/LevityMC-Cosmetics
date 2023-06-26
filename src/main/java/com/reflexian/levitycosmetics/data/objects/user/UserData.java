package com.reflexian.levitycosmetics.data.objects.user;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LTabColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LCrown;
import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LGlow;
import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LHat;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.cosmetics.nickname.AssignedNickname;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.AssignedTitle;
import com.reflexian.levitycosmetics.utilities.uncategorizied.Glow;
import com.reflexian.levitycosmetics.utilities.uncategorizied.GradientUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Getter
public class UserData implements Serializable {
    private UUID uuid;

    public UserData(UUID uuid) {
        this.uuid = uuid;
    }

    private HashSet<String> unknownCosmetics = new HashSet<>();

    private final HashSet<Cosmetic> allCosmetics = new HashSet<>();

    @Setter private HashSet<AssignedNickname> assignedNicknames = new HashSet<>();
    @Setter private HashSet<AssignedTitle> assignedTitles = new HashSet<>();

    @Setter private transient LChatColor selectedChatColor=null;
    @Setter private transient LTabColor selectedTabColor = null;
    @Setter private transient LHat selectedHat=null;
    @Setter private transient LGlow selectedGlow=null;
    @Setter private transient LCrown selectedCrown=null;
    @Setter private transient AssignedTitle selectedTitle=null;
    @Setter private transient AssignedNickname selectedNickname=null;

    private String potentialSelectedNicknameId = "";
    private String potentialSelectedTitleId = "";

    @Setter private int extraPages = 0;
    @Setter private boolean tradeBanned=false;
    private long timestamp=System.currentTimeMillis();

    private Glow glow = null;

    public void reset() {
        selectedChatColor = null;
        selectedTabColor = null;
        selectedHat = null;
        selectedGlow = null;
        selectedCrown = null;
        selectedTitle = null;
        selectedNickname = null;
        extraPages = 0;
        tradeBanned = false;
        timestamp = System.currentTimeMillis();
        allCosmetics.clear();
        assignedNicknames.clear();
        assignedTitles.clear();
        unknownCosmetics.clear();
        if (glow != null) {
            glow.destroy();
        }
    }

    public void equip(Cosmetic cosmetic) {
        final Player player = Bukkit.getPlayer(uuid);
        try {
            if (cosmetic instanceof LChatColor)
                setSelectedChatColor(cosmetic.asChatColor());
            else if (cosmetic instanceof LCrown)
                setSelectedCrown(cosmetic.asCrown());
            else if (cosmetic instanceof LTabColor)
                setSelectedTabColor(cosmetic.asTabColor());
            else if (cosmetic instanceof LGlow) {
                setSelectedGlow(cosmetic.asGlow());
                if (glow != null) glow.destroy();
                this.glow = Glow.builder()
                        .name(player.getName())
                        .player(player)
                        .color(ChatColor.valueOf(cosmetic.asGlow().getColor()))
                        .build();
                this.glow.apply();
            } else if (cosmetic instanceof AssignedNickname) {
                setSelectedNickname((AssignedNickname) cosmetic);
            } else if (cosmetic instanceof AssignedTitle) {
                setSelectedTitle((AssignedTitle) cosmetic);
            } else if (cosmetic instanceof LHat) {
                cosmetic.asHat().equipHat(player);
            }

            player.sendMessage(GradientUtils.colorize(LevityCosmetics.getInstance().getMessagesConfig().getBackpackSelectMessage().replace("%cosmetic%", GradientUtils.colorize(cosmetic instanceof AssignedNickname ? ((AssignedNickname)cosmetic).getContent() : (cosmetic instanceof AssignedTitle ? ((AssignedTitle)cosmetic).getTitle().getName() : cosmetic.getName())))));
            player.closeInventory();
        }catch (Exception exception) {
            exception.printStackTrace();
            player.sendMessage("§cAn error occurred while selecting this cosmetic. Please contact an administrator.");
        }
    }

    public Set<Cosmetic> getAllCosmetics() {
        return allCosmetics;
    }

    public static UserData fromResultSet(ResultSet resultSet) throws SQLException {

        final UserData userData = new UserData(UUID.randomUUID());
        userData.uuid = UUID.fromString(resultSet.getString("user_id"));

        userData.unknownCosmetics = new HashSet<>(Set.of(resultSet.getString("cosmetic_ids").split(";")));
        for (String unknownCosmetic : userData.getUnknownCosmetics()) {
            Cosmetic cosmetic = Cosmetic.getCosmetic(unknownCosmetic);
            if (cosmetic == null) continue;
            userData.getAllCosmetics().add(cosmetic);
        }



        List<String> selectedCosmeticIds = new ArrayList<>(List.of(resultSet.getString("selected_cosmetic_ids").split(";")));

        for (String selectedCosmeticId : selectedCosmeticIds) {
            if (selectedCosmeticId.startsWith("nickname%")) {
                userData.potentialSelectedNicknameId = selectedCosmeticId.replace("nickname%", "");
                continue;
            } else if (selectedCosmeticId.startsWith("title%")) {
                userData.potentialSelectedTitleId = selectedCosmeticId.replace("title%", "");
                continue;
            }
            Cosmetic cosmetic = Cosmetic.getCosmetic(selectedCosmeticId);
            if (cosmetic == null) continue;
            if (cosmetic instanceof LChatColor lChatColor) {
                userData.selectedChatColor = lChatColor;
            } else if (cosmetic instanceof LHat lHat) {
                userData.selectedHat = lHat;
            } else if (cosmetic instanceof LGlow lGlow) {
                userData.selectedGlow = lGlow;
                if (userData.glow != null) userData.glow.destroy();
                userData.glow = Glow.builder()
                        .name(userData.uuid.toString())
                        .color(ChatColor.valueOf(cosmetic.asGlow().getColor()))
                        .build();
            } else if (cosmetic instanceof LTabColor lTabColor) {
                userData.selectedTabColor = lTabColor;
            } else if (cosmetic instanceof LCrown lCrown) {
                userData.selectedCrown = lCrown;
            }
        }

        userData.extraPages = resultSet.getInt("extra_pages");
        userData.timestamp = resultSet.getLong("timestamp");
        userData.tradeBanned = resultSet.getBoolean("trade_banned");
        return userData;
    }


    public Set<String> getDatabaseCosmeticIds() {
        Set<String> cosmeticIds = new HashSet<>();
        for (Cosmetic cosmetic : getAllCosmetics()) {
            cosmeticIds.add(cosmetic.getName());
        }
        cosmeticIds.addAll(getUnknownCosmetics());

        return cosmeticIds;
    }

    public Set<String> getDatabaseSelectedIds() {
        Set<String> cosmeticIds = new HashSet<>();
        if (selectedChatColor != null) cosmeticIds.add(selectedChatColor.getName());
        if (selectedHat != null) cosmeticIds.add(selectedHat.getName());
        if (selectedTitle != null) cosmeticIds.add(selectedTitle.getTitle().getName());
        if (selectedGlow != null) cosmeticIds.add(selectedGlow.getName());
        if (selectedTabColor != null) cosmeticIds.add(selectedTabColor.getName());
        if (selectedCrown != null) cosmeticIds.add(selectedCrown.getName());
        if (selectedNickname != null)  {
            cosmeticIds.add("nickname%" + selectedNickname.getCosmeticId());
            if (selectedNickname.getPaint() != null) {
                cosmeticIds.add(selectedNickname.getPaint().getName());
            }
        }
        if (selectedTitle != null) {
            cosmeticIds.add("title%" + selectedTitle.getCosmeticId());
            if (selectedTitle.getPaint() != null) {
                cosmeticIds.add(selectedTitle.getPaint().getName());
            }
        }
        return cosmeticIds;
    }

    public int getCredits(){
        return (int) LevityCosmetics.getInstance().getEconomy().getBalance(Bukkit.getPlayer(uuid));
    }

    public void removeCredits(int credits){
        LevityCosmetics.getInstance().getEconomy().withdrawPlayer(Bukkit.getPlayer(uuid), credits);
    }
}


