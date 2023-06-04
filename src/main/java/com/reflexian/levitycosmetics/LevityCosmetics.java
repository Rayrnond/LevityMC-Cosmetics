package com.reflexian.levitycosmetics;

import com.reflexian.rapi.RAPI;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.Hibernate;

import java.util.*;

public final class LevityCosmetics extends JavaPlugin {

    @Getter
    private static LevityCosmetics instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        Hibernate.map();
        RAPI rapi = new RAPI(this);
        rapi.init();

        getLogger().info("ez");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
