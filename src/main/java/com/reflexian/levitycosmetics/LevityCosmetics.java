package com.reflexian.levitycosmetics;

import com.reflexian.levitycosmetics.data.database.AsyncClassLoader;
import com.reflexian.levitycosmetics.data.database.AsyncDependencyDownloader;
import com.reflexian.levitycosmetics.data.database.Callback;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;

import java.util.*;

public final class LevityCosmetics extends JavaPlugin {

    @Getter
    private static LevityCosmetics instance;

    @Override
    public void onEnable() {
        instance = this;

        // map of string, string predefined
        List<String> dependencies = Arrays.asList(
                "https://jitpack.io/com/github/Rayrnond/RAPI/b85ffda6b6/RAPI-b85ffda6b6.jar",
                "https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.28/mysql-connector-java-8.0.28.jar",
                "https://repo1.maven.org/maven2/org/hibernate/orm/hibernate-core/6.2.3.Final/hibernate-core-6.2.3.Final.jar"
        );


        // check if data folder exists, if not, create it
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        for (String url : dependencies) {
            AsyncDependencyDownloader.shared.downloadAsync(url, getDataFolder() + "/libs", new Callback() {
                @Override
                public void onSuccess() {
                    getLogger().info("Downloaded " + url + " successfully!");
                }

                @Override
                public void onError(int var1) {
                    getLogger().info("Failed to download " + url + "!");
                    instance.onDisable();
                }

                @Override
                public void onExist() {
                    getLogger().info("Loaded " + url + " from cache!");
                }
            });
        }

        AsyncClassLoader.shared.loadLibrariesAsync(new Callback() {
            @Override
            public void onSuccess() {
                getLogger().info("Loaded libraries successfully!");
            }

            @Override
            public void onError(int var1) {
                getLogger().info("Failed to load libraries!");
                instance.onDisable();
            }

            @Override
            public void onExist() {
                getLogger().info("Libraries already loaded!");
            }
        });

        saveDefaultConfig();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
