package com.reflexian.levitycosmetics.data.configs.cosmetic;

import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LGlow;
import com.reflexian.levitycosmetics.utilities.uncategorizied.ItemBuilder;
import org.bukkit.Material;
import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.Comment;
import pl.mikigal.config.annotation.ConfigName;
import pl.mikigal.config.annotation.ConfigPath;

import java.util.Arrays;
import java.util.List;

@ConfigName("glows.yml")
public interface GlowConfig extends Config {



    @ConfigPath("list")
    @Comment("""
            # This is a list of glowing cosmetics. Each cosmetic in the plugin has a unique id, which is stored in the database.
            # Do not change the id unless you know what you are doing!
            #
            # name: identifier for the cosmetic, no spaces.
            # color: The color of the glow. Should be: BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, PURPLE, YELLOW, WHITE, NONE
            # itemStack: itemstack of the cosmetic in GUI
            
            """)
    default List<LGlow> getGlowings() {

        return Arrays.asList(
                LGlow.builder()
                        .name("AquaGlow")
                        .color("AQUA")
                        .itemStack(new ItemBuilder(Material.STONE_AXE)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:yellow>Click to equip this glow!")
                                .displayname("<color:blue>Aqua Glow")
                                .build())
                        .build(),

                LGlow.builder()
                        .name("RedGlow")
                        .color("RED")
                        .itemStack(new ItemBuilder(Material.DIAMOND_AXE)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:yellow>Click to equip this glow!")
                                .displayname("<color:red>Red Glow")
                                .build())
                        .build()
        );
    }

}
