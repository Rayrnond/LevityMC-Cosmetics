package com.reflexian.levitycosmetics.data.objects.user;

import com.reflexian.levitycosmetics.utilities.Callback;
import com.reflexian.levitycosmetics.utilities.DataService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserDataService extends DataService {

    public static final UserDataService shared = new UserDataService();

    protected final Map<UUID,UserData> cachedUserData = new HashMap<>();

    public void save(UserData userData, Callback<Boolean> success) {
        CompletableFuture.runAsync(()->{
            try (Connection connection = getDatabase().getConnection();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO `userdata` (`user_id`, `cosmetic_ids`, `selected_cosmetic_ids`, `trade_banned`, `timestamp`) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `cosmetic_ids` = ?, `selected_cosmetic_ids` = ?, `trade_banned` = ?, `timestamp` = ?")) {
                statement.setString(1, userData.getUuid().toString());
                statement.setString(2, String.join(";", userData.getCosmeticIds()));
                statement.setString(3, String.join(";", userData.getSelectedCosmetics()));
                statement.setBoolean(4, userData.isTradeBanned());
                statement.setLong(5, userData.getTimestamp());
                statement.setString(6, String.join(";", userData.getCosmeticIds()));
                statement.setString(7, String.join(";", userData.getSelectedCosmetics()));
                statement.setBoolean(8, userData.isTradeBanned());
                statement.setLong(9, userData.getTimestamp());
                statement.executeUpdate();
                success.execute(true);
            } catch (SQLException e) {
                e.printStackTrace();
                success.execute(false);
            }
        });
    }

    public void revokeCache(UUID uuid) {
        cachedUserData.remove(uuid);
    }

    public void cacheUser(UserData userData) {
        cachedUserData.put(userData.getUuid(),userData);
    }

    public UserData retrieveUserFromCache(UUID uuid) {
        return cachedUserData.getOrDefault(uuid,null);
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
