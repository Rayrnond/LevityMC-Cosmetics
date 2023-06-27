package com.reflexian.levitycosmetics.data.objects.user;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.objects.cosmetics.CosmeticType;
import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LTabColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.AssignedHat;
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

    private final HashSet<UserCosmetic> userCosmetics = new HashSet<>();
    private final HashSet<UserCosmetic> selectedCosmetics = new HashSet<>();
//    private final HashSet<Cosmetic> allCosmetics = new HashSet<>();

    @Setter private HashSet<AssignedNickname> assignedNicknames = new HashSet<>();
    @Setter private HashSet<AssignedTitle> assignedTitles = new HashSet<>();
    @Setter private HashSet<AssignedHat> assignedHats = new HashSet<>();

    @Setter private transient LChatColor selectedChatColor=null;
    @Setter private transient LTabColor selectedTabColor = null;
    @Setter private transient LGlow selectedGlow=null;
    @Setter private transient LCrown selectedCrown=null;
    @Setter private transient AssignedHat selectedHat=null;
    @Setter private transient AssignedTitle selectedTitle=null;
    @Setter private transient AssignedNickname selectedNickname=null;

    private String potentialSelectedNicknameId = "";
    private String potentialSelectedTitleId = "";
    private String potentialSelectedHatId = "";

    @Setter private int extraPages = 0;
    @Setter private boolean tradeBanned=false;
    private long timestamp=System.currentTimeMillis();

    private Glow glow = null;

    public void reset() {
        selectedChatColor = null;
        selectedTabColor = null;
        if (selectedHat != null) {
            Bukkit.getPlayer(uuid).getInventory().setHelmet(null);
        }
        selectedHat = null;
        selectedGlow = null;
        selectedCrown = null;
        selectedTitle = null;
        selectedNickname = null;
        extraPages = 0;
        tradeBanned = false;
        timestamp = System.currentTimeMillis();
//        allCosmetics.clear();
        assignedNicknames.clear();
        assignedTitles.clear();
        userCosmetics.clear();
        selectedCosmetics.clear();
        if (glow != null) {
            glow.destroy();
        }
    }

    public void equip(UserCosmetic userCosmetic) {
        selectedCosmetics.add(userCosmetic);
        equip(userCosmetic.getCosmetic());
    }
    public void equip(Cosmetic cosmetic) {
        final Player player = Bukkit.getPlayer(uuid);
        for (UserCosmetic selectedCosmetic : selectedCosmetics) {
            if (selectedCosmetic.getCosmeticType() == cosmetic.getType()) {
                selectedCosmetic.setSelected(false);
            }
        }
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
            } else if (cosmetic instanceof AssignedHat) {
                cosmetic.asHat().equipHat(player);
            }

            String name = cosmetic instanceof AssignedNickname ? ((AssignedNickname)cosmetic).getContent() : (cosmetic instanceof AssignedTitle ? ((AssignedTitle)cosmetic).getTitle().getName() : (cosmetic instanceof AssignedHat ? (((AssignedHat) cosmetic).getLHat()).getName() : cosmetic.getName()));
            player.sendMessage(GradientUtils.colorize(LevityCosmetics.getInstance().getMessagesConfig().getBackpackSelectMessage().replace("%cosmetic%", GradientUtils.colorize(name))));
            player.closeInventory();
        }catch (Exception exception) {
            exception.printStackTrace();
            player.sendMessage("§cAn error occurred while selecting this cosmetic. Please contact an administrator.");
        }
    }

    public void unequip(Cosmetic cosmetic) {
        final Player player = Bukkit.getPlayer(uuid);
        for (UserCosmetic selectedCosmetic : selectedCosmetics) {
            if (selectedCosmetic.getCosmeticType() == cosmetic.getType()) {
                selectedCosmetic.setSelected(false);
            }
        }
        try {
            if (cosmetic instanceof LChatColor)
                setSelectedChatColor(null);
            else if (cosmetic instanceof LCrown)
                setSelectedCrown(null);
            else if (cosmetic instanceof LTabColor)
                setSelectedTabColor(null);
            else if (cosmetic instanceof LGlow) {
                if (glow != null) {
                    glow.destroy();
                }
                setSelectedGlow(null);
            } else if (cosmetic instanceof AssignedNickname) {
                setSelectedNickname(null);
            } else if (cosmetic instanceof AssignedTitle) {
                setSelectedTitle(null);
            } else if (cosmetic instanceof AssignedHat) {
                cosmetic.asHat().unequipHat(this);
            }

            String name = cosmetic instanceof AssignedNickname ? ((AssignedNickname)cosmetic).getContent() : (cosmetic instanceof AssignedTitle ? ((AssignedTitle)cosmetic).getTitle().getName() : (cosmetic instanceof AssignedHat ? (((AssignedHat) cosmetic).getLHat()).getName() : cosmetic.getName()));
            player.sendMessage(GradientUtils.colorize(LevityCosmetics.getInstance().getMessagesConfig().getBackpackUnselectMessage().replace("%cosmetic%", GradientUtils.colorize(name))));
            player.closeInventory();
        }catch (Exception exception) {
            exception.printStackTrace();
            player.sendMessage("§cAn error occurred while unequiping this cosmetic. Please contact an administrator.");
        }
    }

    public static UserData fromResultSet(ResultSet playerResult, ResultSet dataResult) throws SQLException {

        final UserData userData = new UserData(UUID.randomUUID());
        userData.uuid = UUID.fromString(playerResult.getString("user_id"));

        while (dataResult.next()) {
            UserCosmetic userCosmetic = new UserCosmetic(userData.uuid, dataResult.getString("localCosmeticId"), dataResult.getString("playerCosmeticId"), CosmeticType.fromIdentifier(dataResult.getInt("cosmeticType")));
            userCosmetic.setSelected(dataResult.getBoolean("selected"));
            userData.userCosmetics.add(userCosmetic);
        }

        List<UserCosmetic> selectedCosmeticIds = userData.getUserCosmetics().stream().filter(UserCosmetic::isSelected).toList();

        for (UserCosmetic selectedCosmetic : selectedCosmeticIds) {
            if (selectedCosmetic.getCosmeticType() == CosmeticType.ASSIGNED_NICKNAME) {
                userData.potentialSelectedNicknameId = selectedCosmetic.getLocalCosmeticId();
                continue;
            } else if (selectedCosmetic.getCosmeticType() == CosmeticType.ASSIGNED_TITLE) {
                userData.potentialSelectedTitleId = selectedCosmetic.getLocalCosmeticId();
                continue;
            } else if (selectedCosmetic.getCosmeticType() == CosmeticType.ASSIGNED_HAT) {
                userData.potentialSelectedHatId = selectedCosmetic.getLocalCosmeticId();
                continue;
            }
            Cosmetic cosmetic = selectedCosmetic.getCosmetic();
            if (cosmetic == null) continue;
            if (cosmetic instanceof LChatColor lChatColor) {
                userData.selectedChatColor = lChatColor;
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

        userData.extraPages = playerResult.getInt("extra_pages");
        userData.timestamp = playerResult.getLong("timestamp");
        userData.tradeBanned = playerResult.getBoolean("trade_banned");
        return userData;
    }

    public int getCredits(){
        return (int) LevityCosmetics.getInstance().getEconomy().getBalance(Bukkit.getPlayer(uuid));
    }

    public void removeCredits(int credits){
        LevityCosmetics.getInstance().getEconomy().withdrawPlayer(Bukkit.getPlayer(uuid), credits);
    }
}


