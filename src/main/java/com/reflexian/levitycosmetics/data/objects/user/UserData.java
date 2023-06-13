package com.reflexian.levitycosmetics.data.objects.user;

import com.reflexian.levitycosmetics.data.objects.cosmetic.Cosmetic;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Data@AllArgsConstructor
public class UserData implements Serializable {
    private UUID uuid;
    private List<String> cosmeticIds;
    private List<String> selectedCosmetics;
    private boolean tradeBanned;
    private long timestamp;



    public Set<Cosmetic> getAllCosmetics() {
        final Set<Cosmetic> cosmetics = new HashSet<>();
        for (String cosmeticId : cosmeticIds) {
            Cosmetic cosmetic = Cosmetic.getCosmetic(cosmeticId);
            if (cosmetic != null) {
                cosmetics.add(cosmetic);
            }
        }
        return cosmetics;
    }

    public static UserData fromResultSet(ResultSet resultSet) throws SQLException {
        UUID userId = UUID.fromString(resultSet.getString("user_id"));
        List<String> cosmeticIds = new ArrayList<>();
        Collections.addAll(cosmeticIds, resultSet.getString("cosmetic_ids").split(";"));

        List<String> selectedCosmeticIds = new ArrayList<>();
        Collections.addAll(selectedCosmeticIds, resultSet.getString("selected_cosmetic_ids").split(";"));

        long timestamp = resultSet.getLong("timestamp");
        boolean tradeBanned = resultSet.getBoolean("trade_banned");

        return new UserData(userId, cosmeticIds, selectedCosmeticIds,  tradeBanned, timestamp);
    }
}


