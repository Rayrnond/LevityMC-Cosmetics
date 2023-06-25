package com.reflexian.levitycosmetics.data.configs.cosmetic;

import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LChatColor;
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
            # name: identifier for the cosmetic, no spaces.
            # color: The color of the message, always include %message% which is replaced with the message.
            # itemStack: itemstack of the cosmetic in GUI
            
            """)
    default List<LChatColor> getChatColors() {
        return Arrays.asList(
                LChatColor.builder()
                        .name("FireGradientChatColor")
                        .color("<gradient:#FF0000:#FF4500>%message%</gradient>")
                        .itemStack(new ItemBuilder(Material.BLAZE_POWDER)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:yellow>Click to equip this chat color!")
                                .displayname("<gradient:#FF0000:#FF4500>Fire Gradient Chat Color</gradient>")
                                .build())
                        .build(),

                LChatColor.builder()
                        .name("OceanGradientChatColor")
                        .color("<gradient:#0000FF:#00FFFF>%message%</gradient>")
                        .itemStack(new ItemBuilder(Material.PRISMARINE_SHARD)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:yellow>Click to equip this chat color!")
                                .displayname("<gradient:#0000FF:#00FFFF>Ocean Gradient Chat Color</gradient>")
                                .build())
                        .build(),

                LChatColor.builder()
                        .name("GalaxyGradientChatColor")
                        .color("<gradient:#5500FF:#AA00FF>%message%</gradient>")
                        .itemStack(new ItemBuilder(Material.NETHER_STAR)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:yellow>Click to equip this chat color!")
                                .displayname("<gradient:#5500FF:#AA00FF>Galaxy Gradient Chat Color</gradient>")
                                .build())
                        .build(),

                LChatColor.builder()
                        .name("RainbowGradientChatColor")
                        .color("<rainbow>%message%</rainbow>")
                        .itemStack(new ItemBuilder(Material.PRISMARINE_CRYSTALS)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:yellow>Click to equip this chat color!")
                                .displayname("<rainbow>Rainbow Gradient Chat Color<rainbow>")
                                .build())
                        .build(),

                LChatColor.builder()
                        .name("EtherealGradientChatColor")
                        .color("<gradient:#FF69B4:#800080>%message%</gradient>")
                        .itemStack(new ItemBuilder(Material.ENDER_PEARL)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:yellow>Click to equip this chat color!")
                                .displayname("<gradient:#FF69B4:#800080>Ethereal Gradient Chat Color</gradient>")
                                .build())
                        .build()
        );
    }

}
