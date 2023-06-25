package com.reflexian.levitycosmetics.listeners;

import com.reflexian.levitycosmetics.data.configs.ConfigurationLoader;
import com.reflexian.rapi.api.annotation.Registrar;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Registrar
public class ItemsAdderListener implements Listener {

    @EventHandler
    public void onItemsLoad(ItemsAdderLoadDataEvent event) {
        ConfigurationLoader.init();
    }

}
