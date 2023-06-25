package com.reflexian.levitycosmetics.listeners;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.levitycosmetics.utilities.uncategorizied.GradientUtils;
import com.reflexian.rapi.api.annotation.Registrar;
import lombok.SneakyThrows;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.Audiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.xezard.glow.data.glow.Glow;

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
