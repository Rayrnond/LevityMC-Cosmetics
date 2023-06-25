package com.reflexian.levitycosmetics.data.configs;

import com.reflexian.levitycosmetics.utilities.uncategorizied.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.Comment;
import pl.mikigal.config.annotation.ConfigName;
import pl.mikigal.config.annotation.ConfigPath;

@ConfigName("config.yml")
@Comment("""
        This is the config file for the plugin. You can change the database settings here.
        """)
public interface DefaultConfig extends Config {


    @Comment("This is the host for the database")
    @ConfigPath("mysql.host")
    default String getDatabaseHost() {
        return "127.0.0.1";
    }
    @Comment("This is the port for the database")
    @ConfigPath("mysql.port")
    default int getDatabasePort() {
        return 3306;
    }
    @Comment("This is the database")
    @ConfigPath("mysql.database")
    default String getDatabaseName() {
        return "levitycosmetics";
    }
    @Comment("This is the username for the database")
    @ConfigPath("mysql.username")
    default String getDatabaseUsername() {
        return "root";
    }
    @Comment("This is the password for the database")
    @ConfigPath("mysql.password")
    default String getDatabasePassword() {
        return "password";
    }


    @Comment("Default backpack pages")
    @ConfigPath("backpack.default-pages")
    default int getBackpackPages() {
        return 3;
    }

    @Comment("The price per backpack page")
    @ConfigPath("backpack.additional-price")
    default int getBackpackPrice() {
        return 1000;
    }

    @Comment("This is the item used for nickname tickets")
    @ConfigPath("items.nickname.ticket")
    default ItemStack getNicknameTicket() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.NAME_TAG);
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<color:red>Nickname Ticket");
        itemBuilder.lore("<color:gray>Use this ticket redeem a custom name!","<color:gray>Right click to use!");
        return itemBuilder.build();
    }

    @Comment("This is the item shown in backpack")
    @ConfigPath("items.nickname.backpack")
    default ItemStack getNicknameCosmeticBackpackItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.NAME_TAG);
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<color:red>Nickname: %nickname%");
        itemBuilder.lore("<color:gray>Click to select this nickname!");
        return itemBuilder.build();
    }

}
