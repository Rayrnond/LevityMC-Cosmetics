package com.reflexian.levitycosmetics.data.configs.cosmetic;

import com.reflexian.levitycosmetics.data.objects.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.utilities.uncategorizied.ItemBuilder;
import org.bukkit.Material;
import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.Comment;
import pl.mikigal.config.annotation.ConfigName;
import pl.mikigal.config.annotation.ConfigPath;

import java.util.Arrays;
import java.util.List;

@ConfigName("chatcolors.yml")
public interface ChatColorConfig extends Config {



    @ConfigPath("list")
    @Comment("""
            # This is a list of chatcolor cosmetics. Each cosmetic in the plugin has a unique id, which is stored in the database.
            # Do not change the id unless you know what you are doing!
            #
            # id: identifier for the cosmetic.
            # name: name of the cosmetic. 
            # itemStack: itemstack of the cosmetic in GUI
            
            """)
    default List<LChatColor> getChatColors() {
        return Arrays.asList(
                LChatColor.builder()
                        .id("0")
                        .name("&bSolid Chat Color")
                        .color("&b%message%")
                        .itemStack(new ItemBuilder(Material.GOLDEN_CARROT)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("&7Click to equip this chat color!")
                                .displayname("&bSolid Chat Color")
                                .build())
                        .build(),

                LChatColor.builder()
                        .id("1")
                        .name("<#F5C9C9>Red Gradient Chat Color %s<#B8A4C9>")
                        .color("<#F5C9C9>%message%<#B8A4C9>")
                        .itemStack(new ItemBuilder(Material.ALLAY_SPAWN_EGG)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("&7Click to equip this chat color!")
                                .displayname("<#F5C9C9>Red Gradient Chat Color<#B8A4C9>")
                                .build())
                        .build(),

                LChatColor.builder()
                        .id("2")
                        .name("<#F5C9C9>Another Chat Color<#B8A4C9>")
                        .color("<#F5C9C9>%message%<#B8A4C9>")
                        .itemStack(new ItemBuilder(Material.RED_CARPET)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("&7Click to equip this chat color!")
                                .displayname("<#F5C9C9>Another Chat Color<#B8A4C9>")
                                .build())
                        .build()
        );
    }

}