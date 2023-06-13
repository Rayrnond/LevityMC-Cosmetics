package com.reflexian.levitycosmetics;

import com.reflexian.levitycosmetics.data.Database;
import com.reflexian.levitycosmetics.data.configs.ChatColorConfig;
import com.reflexian.levitycosmetics.data.configs.DefaultConfig;
import com.reflexian.levitycosmetics.data.configs.GUIConfig;
import com.reflexian.levitycosmetics.data.configs.MessagesConfig;
import com.reflexian.levitycosmetics.utilities.ConfigItemSerializer;
import com.reflexian.rapi.RAPI;
import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mikigal.config.ConfigAPI;
import pl.mikigal.config.style.CommentStyle;
import pl.mikigal.config.style.NameStyle;

import java.io.File;

@Getter
public final class LevityCosmetics extends JavaPlugin {

    @Getter
    private static LevityCosmetics instance;

    private DefaultConfig defaultConfig;
    private MessagesConfig messagesConfig;

    @Setter private ChatColorConfig chatColorConfig;
    @Setter private GUIConfig guiConfig;
    private InventoryManager inventoryManager;


    @Override
    public void onEnable() {
        instance = this;

        ConfigAPI.registerSerializer(ItemStack.class, new ConfigItemSerializer());
        defaultConfig = ConfigAPI.init(DefaultConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, true, this);
        messagesConfig = ConfigAPI.init(MessagesConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, true, this);

        RAPI rapi = new RAPI(this);
        rapi.init();

        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        // async
        Bukkit.getScheduler().runTaskAsynchronously(this, Database.shared::initializeDataSource);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
