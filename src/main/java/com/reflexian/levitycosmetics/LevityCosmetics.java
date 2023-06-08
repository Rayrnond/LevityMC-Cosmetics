package com.reflexian.levitycosmetics;

import com.reflexian.levitycosmetics.data.Database;
import com.reflexian.rapi.RAPI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LevityCosmetics extends JavaPlugin {

    @Getter
    private static LevityCosmetics instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        RAPI rapi = new RAPI(this);
        rapi.init();

        getLogger().info("ez");
        // async
        Bukkit.getScheduler().runTaskAsynchronously(this, Database.shared::initializeDataSource);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
