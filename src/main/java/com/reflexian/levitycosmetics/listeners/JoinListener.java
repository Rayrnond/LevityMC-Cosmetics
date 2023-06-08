package com.reflexian.levitycosmetics.listeners;

import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataDoa;
import com.reflexian.rapi.api.annotation.Registrar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

@Registrar
public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        UserData userData = UserDataDoa.shared.getUserDataByUUID(event.getUniqueId());
        System.out.println(userData);
    }

}
