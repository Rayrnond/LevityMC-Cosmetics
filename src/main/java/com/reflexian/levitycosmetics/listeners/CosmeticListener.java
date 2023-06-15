package com.reflexian.levitycosmetics.listeners;

import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.levitycosmetics.utilities.uncategorizied.GradientUtils;
import com.reflexian.rapi.api.annotation.Registrar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Registrar
public class CosmeticListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        UserData userData = UserDataService.shared.retrieveUserFromCache(event.getPlayer().getUniqueId());
        if (userData.getSelectedChatColor() != null) {
            event.setMessage(GradientUtils.colorize(userData.getSelectedChatColor().getColor().replace("%message%", event.getMessage())));
        }
    }

}
