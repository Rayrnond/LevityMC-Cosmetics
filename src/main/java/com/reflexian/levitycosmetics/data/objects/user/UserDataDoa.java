package com.reflexian.levitycosmetics.data.objects.user;

import com.reflexian.levitycosmetics.data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class UserDataDoa {
    private static final String INSERT_QUERY = "INSERT INTO userdata (user_id, cosmetic_ids, timestamp) VALUES (?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT * FROM userdata WHERE user_id = ?";

    private Database database;

    public static final UserDataDoa shared = new UserDataDoa();

    public UserDataDoa() {
        database = Database.shared;
    }

    public void saveUserData(UserData userData) {
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, userData.getUuid().toString());
            statement.setString(2, String.join(",", userData.getCosmeticIds()));
            statement.setLong(3, userData.getTimestamp());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserData getUserDataByUUID(UUID userId) {
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_QUERY)) {
            statement.setString(1, userId.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    List<String> cosmeticIds = new ArrayList<>();
                    String[] ids = resultSet.getString("cosmetic_ids").split(",");
                    Collections.addAll(cosmeticIds, ids);
                    Long timestamp = resultSet.getLong("timestamp");

                    return new UserData(userId, cosmeticIds, timestamp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
