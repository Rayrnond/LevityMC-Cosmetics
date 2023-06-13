package com.reflexian.levitycosmetics.data.objects.user;

import com.reflexian.levitycosmetics.utilities.Callback;
import com.reflexian.levitycosmetics.utilities.DataService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserDataService extends DataService {

    public static final UserDataService shared = new UserDataService();

    public void save(UserData userData, Callback<Boolean> success) {
        CompletableFuture.runAsync(()->{
            try (Connection connection = getDatabase().getConnection();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO `userdata` (`user_id`, `cosmetic_ids`, `trade_banned`, `timestamp`) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE `cosmetic_ids` = ?, `trade_banned` = ?, `timestamp` = ?")) {
                statement.setString(1, userData.getUuid().toString());
                statement.setString(2, String.join(",", userData.getCosmeticIds()));
                statement.setBoolean(3, userData.isTradeBanned());
                statement.setLong(4, userData.getTimestamp());
                statement.setString(5, String.join(",", userData.getCosmeticIds()));
                statement.setBoolean(6, userData.isTradeBanned());
                statement.setLong(7, userData.getTimestamp());
                statement.executeUpdate();
                success.execute(true);
            } catch (SQLException e) {
                e.printStackTrace();
                success.execute(false);
            }
        });
    }

    public void retrieveUserFromUUID(UUID uuid, Callback<UserData> user) {
        CompletableFuture.runAsync(()->{
            try (Connection connection = getDatabase().getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM `userdata` WHERE `user_id` = ?")) {
                statement.setString(1, uuid.toString());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        user.execute(UserData.fromResultSet(resultSet));
                    } else {
                        user.execute(null);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                user.execute(null);
            }
        });
    }
}
