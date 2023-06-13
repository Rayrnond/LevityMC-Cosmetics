package com.reflexian.levitycosmetics.data.objects.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data@AllArgsConstructor
public class UserData implements Serializable {
    private UUID uuid;
    private List<String> cosmeticIds;
    private boolean tradeBanned;
    private long timestamp;



    public static UserData fromResultSet(ResultSet resultSet) throws SQLException {
        UUID userId = UUID.fromString(resultSet.getString("user_id"));
        List<String> cosmeticIds = new ArrayList<>();
        cosmeticIds.add(resultSet.getString("cosmetic_ids"));
        long timestamp = resultSet.getLong("timestamp");
        boolean tradeBanned = resultSet.getBoolean("trade_banned");

        return new UserData(userId, cosmeticIds, tradeBanned, timestamp);
    }
}


