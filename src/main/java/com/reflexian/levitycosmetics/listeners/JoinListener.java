package com.reflexian.levitycosmetics.listeners;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.rapi.api.annotation.Registrar;
import lombok.SneakyThrows;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

@Registrar
public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        UserDataService.shared.retrieveUserFromUUID(event.getUniqueId(),e->{
            if (e == null ) {
                UserData userData = new UserData(event.getUniqueId());
                UserDataService.shared.cacheUser(userData);
                UserDataService.shared.save(userData,success -> {
                    if (!success) {
                        LevityCosmetics.getInstance().getLogger().severe("[!] Failed to save player " + event.getName());
                    }
                });
            } else {
                UserDataService.shared.cacheUser(e);
            }
        });
    }

    @SneakyThrows
    @EventHandler
    public void onActualJoin(PlayerJoinEvent event) {
        for (UserData value : UserDataService.shared.getCachedUserData().values()) {

            if (value.getUuid() == event.getPlayer().getUniqueId()) {
                if (value.getGlow() != null) {
                    value.getGlow().setPlayer(event.getPlayer());
                    value.getGlow().apply();
                }
            }

//            if (value.getSelectedGlow() != null) {
//                value.getGlow().display(event.getPlayer());
//            }
        }
    }


}
