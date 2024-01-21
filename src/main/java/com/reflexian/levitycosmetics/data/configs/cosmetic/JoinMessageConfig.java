package com.reflexian.levitycosmetics.data.configs.cosmetic;

import com.reflexian.levitycosmetics.data.objects.cosmetics.joinmessage.LJoinMessage;
import com.reflexian.levitycosmetics.utilities.uncategorizied.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.Comment;
import pl.mikigal.config.annotation.ConfigName;
import pl.mikigal.config.annotation.ConfigPath;

import java.util.Arrays;
import java.util.List;

@ConfigName("joinmessages.yml")
public interface JoinMessageConfig extends Config {



    @ConfigPath("list")
    @Comment("""
            # This is a list of join message cosmetics. Each cosmetic in the plugin has a unique id, which is stored in the database.
            # Do not change the id unless you know what you are doing!
            #
            # name: identifier for the cosmetic, no spaces.
            # message: The message, replaces %player% or %playerWithColor% with the player name.
            # itemStack: itemstack of the cosmetic in GUI
            
            """)
    default List<LJoinMessage> getJoinMessages() {
        return Arrays.asList(
                LJoinMessage.builder()
                        .name("Red")
                        .message("<color:red>%player%")
                        .itemStack(new ItemBuilder(Material.FIRE_CHARGE)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:yellow>Click to equip the join message crown!")
                                .displayname("<color:red>Red Join Message")
                                .build())
                        .build()
        );
    }

}
