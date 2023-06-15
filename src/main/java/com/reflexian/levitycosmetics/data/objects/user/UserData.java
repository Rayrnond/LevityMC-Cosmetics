package com.reflexian.levitycosmetics.data.objects.user;

import com.reflexian.levitycosmetics.data.objects.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.cosmetic.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.hat.LHat;
import com.reflexian.levitycosmetics.data.objects.titles.LTitle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private Set<String> unknownCosmetics = new HashSet<>();
    private Set<Cosmetic> allCosmetics = new HashSet<>();

    @Setter
    private transient LChatColor selectedChatColor=null;
    @Setter
    private transient LHat selectedHat=null;
    @Setter
    private transient LTitle selectedTitle=null;


    private boolean tradeBanned=false;
    private long timestamp=System.currentTimeMillis();



    public Set<Cosmetic> getAllCosmetics() {
        return allCosmetics;
    }

    public static UserData fromResultSet(ResultSet resultSet) throws SQLException {

        final UserData userData = new UserData(UUID.randomUUID());
        userData.uuid = UUID.fromString(resultSet.getString("user_id"));

        userData.unknownCosmetics = Set.of(resultSet.getString("cosmetic_ids").split(";"));
        for (String unknownCosmetic : userData.getUnknownCosmetics()) {
            Cosmetic cosmetic = Cosmetic.getCosmetic(unknownCosmetic);
            if (cosmetic == null) continue;
            userData.getAllCosmetics().add(cosmetic);
        }

        List<String> selectedCosmeticIds = new ArrayList<>(List.of(resultSet.getString("selected_cosmetic_ids").split(";")));

        for (String selectedCosmeticId : selectedCosmeticIds) {
            Cosmetic cosmetic = Cosmetic.getCosmetic(selectedCosmeticId);
            if (cosmetic == null) continue;
            if (cosmetic instanceof LChatColor lChatColor) {
                userData.selectedChatColor = lChatColor;
            } else if (cosmetic instanceof LHat lHat) {
                userData.selectedHat = lHat;
            } else if (cosmetic instanceof LTitle lTitle) {
                userData.selectedTitle = lTitle;
            }
        }

        userData.timestamp = resultSet.getLong("timestamp");
        userData.tradeBanned = resultSet.getBoolean("trade_banned");
        return userData;
    }

    public Set<String> getDatabaseCosmeticIds() {
        Set<String> cosmeticIds = new HashSet<>();
        for (Cosmetic cosmetic : getAllCosmetics()) {
            cosmeticIds.add(cosmetic.getUniqueId());
        }
        cosmeticIds.addAll(getUnknownCosmetics());

        return cosmeticIds;
    }

    public Set<String> getDatabaseSelectedIds() {
        Set<String> cosmeticIds = new HashSet<>();
        if (selectedChatColor != null) cosmeticIds.add(selectedChatColor.getUniqueId());
        if (selectedHat != null) cosmeticIds.add(selectedHat.getUniqueId());
        if (selectedTitle != null) cosmeticIds.add(selectedTitle.getUniqueId());
        return cosmeticIds;
    }
}


