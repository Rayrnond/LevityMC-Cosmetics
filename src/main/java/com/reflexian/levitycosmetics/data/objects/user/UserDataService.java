package com.reflexian.levitycosmetics.data.objects.user;

import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.AssignedHat;
import com.reflexian.levitycosmetics.data.objects.cosmetics.nickname.AssignedNickname;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.AssignedTitle;
import com.reflexian.levitycosmetics.utilities.uncategorizied.Callback;
import com.reflexian.levitycosmetics.utilities.uncategorizied.DataService;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class UserDataService extends DataService {

    public static final UserDataService shared = new UserDataService();

    @Getter
    protected final Map<UUID,UserData> cachedUserData = new HashMap<>();

    public void save(UserData userData, Callback<Boolean> success) {
        CompletableFuture.runAsync(() -> {
            try (Connection connection = getDatabase().getConnection()) {
                String upsertQuery = "INSERT INTO `userdata` (`user_id`, `extra_pages`, `trade_banned`, `timestamp`) " +
                        "VALUES (?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE `extra_pages` = VALUES(`extra_pages`), " +
                        "`trade_banned` = VALUES(`trade_banned`), `timestamp` = VALUES(`timestamp`)";

                try (PreparedStatement statement = connection.prepareStatement(upsertQuery)) {
                    statement.setString(1, userData.getUuid().toString());
                    statement.setInt(2, userData.getExtraPages());
                    statement.setBoolean(3, userData.isTradeBanned());
                    statement.setLong(4, userData.getTimestamp());
                    statement.executeUpdate();
                }

                String deleteQuery = "DELETE FROM `playercosmetics` WHERE `user_id` = ?";

                try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                    statement.setString(1, userData.getUuid().toString());
                    statement.executeUpdate();
                }

                String insertQuery = "INSERT INTO `playercosmetics` (user_id, localCosmeticId, playerCosmeticId, cosmeticType, selected) " +
                        "VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                    connection.setAutoCommit(false);

                    for (UserCosmetic userCosmetic : userData.getUserCosmetics()) {
                        statement.setString(1, userData.getUuid().toString());
                        statement.setString(2, userCosmetic.getLocalCosmeticId());
                        statement.setString(3, userCosmetic.getPlayerCosmeticId());
                        statement.setInt(4, userCosmetic.getCosmeticType().getIdentifier());
                        statement.setBoolean(5, userCosmetic.isSelected());
                        statement.addBatch();
                    }

                    statement.executeBatch();
                    connection.commit();
                    success.execute(true);
                }

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
            try (Connection connection = getDatabase().getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM `userdata` WHERE `user_id` = ?"); PreparedStatement dataStatement = connection.prepareStatement("SELECT * FROM `playercosmetics` WHERE `user_id` = ?")) {
                statement.setString(1, uuid.toString());
                dataStatement.setString(1, uuid.toString());
                try (ResultSet resultSet = statement.executeQuery(); ResultSet dataResultSet = dataStatement.executeQuery()) {
                    if (resultSet.next()) {
                        final UserData userData = UserData.fromResultSet(resultSet, dataResultSet);
                        queryNicknames(uuid, nicknames -> {
                            if (nicknames != null) {
                                UserData u = retrieveUserFromCache(uuid);
                                String potentialSelectedNicknameId = u.getPotentialSelectedNicknameId();
                                if (!potentialSelectedNicknameId.isEmpty()) {
                                    Optional<AssignedNickname> selectedNickname = nicknames.stream()
                                            .filter(e -> e.getCosmeticId().equals(potentialSelectedNicknameId))
                                            .findFirst();
                                    selectedNickname.ifPresent(u::setSelectedNickname);
                                }
                                u.setAssignedNicknames(nicknames);
                            }
                        });

                        queryTitles(uuid, titles -> {
                            if (titles != null) {
                                UserData u = retrieveUserFromCache(uuid);
                                String potentialSelectedTitleId = u.getPotentialSelectedTitleId();
                                if (!potentialSelectedTitleId.isEmpty()) {
                                    Optional<AssignedTitle> selectedNickname = titles.stream()
                                            .filter(e -> e.getCosmeticId().equals(potentialSelectedTitleId))
                                            .findFirst();
                                    selectedNickname.ifPresent(u::setSelectedTitle);
                                }
                                u.setAssignedTitles(titles);
                            }
                        });

                        queryHats(uuid, hats -> {
                            if (hats != null) {
                                UserData u = retrieveUserFromCache(uuid);
                                String potentialSelectedHatId = u.getPotentialSelectedHatId();
                                if (!potentialSelectedHatId.isEmpty()) {
                                    Optional<AssignedHat> selectedNickname = hats.stream()
                                            .filter(e -> e.getCosmeticId().equals(potentialSelectedHatId))
                                            .findFirst();
                                    selectedNickname.ifPresent(u::setSelectedHat);
                                }
                                u.setAssignedHats(hats);
                            }
                        });
                        user.execute(userData);
                    } else {
                        user.execute(null);
                    }
                } catch (Exception e ) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                user.execute(null);
            }
        });
    }

    public void queryNicknames(UUID uuid, Callback<HashSet<AssignedNickname>> nicknames) {
        CompletableFuture.runAsync(()->{
            try (Connection connection = getDatabase().getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM `nicknames` WHERE `user_id` = ?")) {
                statement.setString(1, uuid.toString());
                try (ResultSet resultSet = statement.executeQuery()) {
                    HashSet<AssignedNickname> a = new HashSet<>();
                    while (resultSet.next()) {
                        a.add(AssignedNickname.fromResultSet(resultSet));
                    }
                    nicknames.execute(a);
                } catch (Exception e) {
                    e.printStackTrace();
                    nicknames.execute(null);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                nicknames.execute(null);
            }
        });
    }
    public void queryTitles(UUID uuid, Callback<HashSet<AssignedTitle>> titles) {
        CompletableFuture.runAsync(()->{
            try (Connection connection = getDatabase().getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM `titles` WHERE `user_id` = ?")) {
                statement.setString(1, uuid.toString());
                try (ResultSet resultSet = statement.executeQuery()) {
                    HashSet<AssignedTitle> a = new HashSet<>();
                    while (resultSet.next()) {
                        a.add(AssignedTitle.fromResultSet(resultSet));
                    }
                    titles.execute(a);
                } catch (Exception e) {
                    e.printStackTrace();
                    titles.execute(null);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                titles.execute(null);
            }
        });
    }

    public void queryHats(UUID uuid, Callback<HashSet<AssignedHat>> hats) {
        CompletableFuture.runAsync(()->{
            try (Connection connection = getDatabase().getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM `hats` WHERE `user_id` = ?")) {
                statement.setString(1, uuid.toString());
                try (ResultSet resultSet = statement.executeQuery()) {
                    HashSet<AssignedHat> a = new HashSet<>();
                    while (resultSet.next()) {
                        a.add(AssignedHat.fromResultSet(resultSet));
                    }
                    hats.execute(a);
                } catch (Exception e) {
                    e.printStackTrace();
                    hats.execute(null);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                hats.execute(null);
            }
        });
    }

}
