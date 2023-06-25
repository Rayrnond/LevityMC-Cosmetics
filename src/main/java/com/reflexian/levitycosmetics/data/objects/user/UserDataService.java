package com.reflexian.levitycosmetics.data.objects.user;

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
        CompletableFuture.runAsync(()->{
            try (Connection connection = getDatabase().getConnection();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO `userdata` (`user_id`, `cosmetic_ids`, `selected_cosmetic_ids`, `extra_pages`, `trade_banned`, `timestamp`) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `cosmetic_ids` = ?, `selected_cosmetic_ids` = ?, `extra_pages` = ?, `trade_banned` = ?, `timestamp` = ?")) {
                statement.setString(1, userData.getUuid().toString());
                statement.setString(2, String.join(";", userData.getDatabaseCosmeticIds()));
                statement.setString(3, String.join(";", userData.getDatabaseSelectedIds()));
                statement.setInt(4, userData.getExtraPages());
                statement.setBoolean(5, userData.isTradeBanned());
                statement.setLong(6, userData.getTimestamp());
                statement.setString(7, String.join(";", userData.getDatabaseCosmeticIds()));
                statement.setString(8, String.join(";", userData.getDatabaseSelectedIds()));
                statement.setInt(9, userData.getExtraPages());
                statement.setBoolean(10, userData.isTradeBanned());
                statement.setLong(11, userData.getTimestamp());
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
                        final UserData userData = UserData.fromResultSet(resultSet);
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
}
