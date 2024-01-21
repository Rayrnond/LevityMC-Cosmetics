package com.reflexian.levitycosmetics.listeners;

import com.reflexian.levitycosmetics.data.configs.ConfigurationLoader;
import com.reflexian.rapi.api.annotation.Registrar;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Registrar
public class ItemsAdderListener implements Listener {

    private static boolean loaded = false;

    @EventHandler
    public void onItemsLoad(ItemsAdderLoadDataEvent event) {
        if (loaded) return;
        loaded = true;
        ConfigurationLoader.init();
    }

    public static void onLoad() {
        if (loaded) return;
        loaded = true;
        ConfigurationLoader.init();
    }

}
