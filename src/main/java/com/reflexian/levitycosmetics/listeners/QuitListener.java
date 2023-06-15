package com.reflexian.levitycosmetics.listeners;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.rapi.api.annotation.Registrar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@Registrar
public class QuitListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        UserDataService.shared.save(UserDataService.shared.retrieveUserFromCache(event.getPlayer().getUniqueId()), e->{
            if (!e) {
                LevityCosmetics.getInstance().getLogger().severe("[!] Failed to save player data for " + event.getPlayer().getName() + " ("+ event.getPlayer().getUniqueId() +")");
            }
        });
        UserDataService.shared.revokeCache(event.getPlayer().getUniqueId());
    }


}
