package com.reflexian.levitycosmetics.listeners;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.levitycosmetics.utilities.uncategorizied.GradientUtils;
import com.reflexian.rapi.api.annotation.Registrar;
import lombok.SneakyThrows;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
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

        UserData userData = UserDataService.shared.retrieveUserFromCache(event.getPlayer().getUniqueId());
        if (userData==null) {
            Bukkit.getScheduler().runTaskLater(LevityCosmetics.getInstance(), ()->{
                onActualJoin(event);
                LevityCosmetics.getInstance().getLogger().warning("Failed to load user data for " + event.getPlayer().getName() + ". Retrying in 40 ticks...");
            },40);
            return;
        }
        if (userData.getGlow() != null) {
            userData.getGlow().setPlayer(event.getPlayer());
            userData.getGlow().apply();
        }
        if (userData.getSelectedHat() != null) {
            userData.getSelectedHat().equipHat(event.getPlayer());
        }
        if (userData.getSelectedJoinMessage()!=null) {
            event.setJoinMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), "%levitycosmetics_joinmessage%"));
        }
    }


}
