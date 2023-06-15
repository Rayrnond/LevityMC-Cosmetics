package com.reflexian.levitycosmetics.listeners;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.configs.cosmetic.ChatColorConfig;
import com.reflexian.levitycosmetics.data.configs.GUIConfig;
import com.reflexian.levitycosmetics.data.configs.cosmetic.TitleConfig;
import com.reflexian.rapi.api.annotation.Registrar;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.mikigal.config.ConfigAPI;
import pl.mikigal.config.style.CommentStyle;
import pl.mikigal.config.style.NameStyle;

import java.io.File;

@Registrar
public class ItemsAdderListener implements Listener {

    @EventHandler
    public void onItemsLoad(ItemsAdderLoadDataEvent event) {
        var instance = LevityCosmetics.getInstance();
        instance.setChatColorConfig(ConfigAPI.init(ChatColorConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, false, new File(instance.getDataFolder()+"/cosmetics/"), instance));
        instance.setTitleConfig(ConfigAPI.init(TitleConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, false, new File(instance.getDataFolder()+"/cosmetics/"), instance));
        instance.setGuiConfig(ConfigAPI.init(GUIConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, false, instance));
    }

}
