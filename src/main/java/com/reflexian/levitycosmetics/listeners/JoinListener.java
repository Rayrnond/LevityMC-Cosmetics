package com.reflexian.levitycosmetics.listeners;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.configs.ChatColorConfig;
import com.reflexian.levitycosmetics.data.configs.GUIConfig;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.levitycosmetics.utilities.GradientUtils;
import com.reflexian.rapi.api.annotation.Registrar;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.mikigal.config.ConfigAPI;
import pl.mikigal.config.style.CommentStyle;
import pl.mikigal.config.style.NameStyle;

import java.io.File;
import java.util.ArrayList;

@Registrar
public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        UserDataService.shared.retrieveUserFromUUID(event.getUniqueId(),e->{
            if (e == null ) {
                UserData userData = new UserData(event.getUniqueId(), new ArrayList<>(), new ArrayList<>(), false, System.currentTimeMillis());
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

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        UserDataService.shared.revokeCache(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onItmesLoad(ItemsAdderLoadDataEvent event) {
        LevityCosmetics.getInstance().setChatColorConfig(ConfigAPI.init(ChatColorConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, false, new File(LevityCosmetics.getInstance().getDataFolder()+"/cosmetics/"), LevityCosmetics.getInstance()));
        LevityCosmetics.getInstance().setGuiConfig(ConfigAPI.init(GUIConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, false, LevityCosmetics.getInstance()));
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setMessage(GradientUtils.colorize(LevityCosmetics.getInstance().getChatColorConfig().getChatColors().get(1).getColor().replace("%message%", event.getMessage())));
    }

}
