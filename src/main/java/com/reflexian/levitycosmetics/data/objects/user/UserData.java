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
    private Long timestamp;



    public static UserData fromResultSet(ResultSet resultSet) throws SQLException {
        UUID userId = UUID.fromString(resultSet.getString("user_id"));
        List<String> cosmeticIds = new ArrayList<>();
        cosmeticIds.add(resultSet.getString("cosmetic_id"));
        Long timestamp = resultSet.getLong("timestamp");

        return new UserData(userId, cosmeticIds, timestamp);
    }
}


