package com.reflexian.levitycosmetics.data.configs.cosmetic;

import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LCrown;
import com.reflexian.levitycosmetics.utilities.uncategorizied.ItemBuilder;
import org.bukkit.Material;
import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.Comment;
import pl.mikigal.config.annotation.ConfigName;
import pl.mikigal.config.annotation.ConfigPath;

import java.util.Arrays;
import java.util.List;

@ConfigName("crowns.yml")
public interface CrownConfig extends Config {



    @ConfigPath("list")
    @Comment("""
            # This is a list of crown cosmetics. Each cosmetic in the plugin has a unique id, which is stored in the database.
            # Do not change the id unless you know what you are doing!
            #
            # name: identifier for the cosmetic, no spaces.
            # symbol: The symbol for the crown.
            # itemStack: itemstack of the cosmetic in GUI
            
            """)
    default List<LCrown> getCrowns() {
        return Arrays.asList(
                LCrown.builder()
                        .name("Money")
                        .symbol("<color:yellow>$")
                        .itemStack(new ItemBuilder(Material.FIRE_CHARGE)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:yellow>Click to equip the money crown!")
                                .displayname("<color:yellow>Money Crown")
                                .build())
                        .build(),

                LCrown.builder()
                        .name("Heart")
                        .symbol("<color:red>❤")
                        .itemStack(new ItemBuilder(Material.RED_DYE)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:yellow>Click to equip this crown!")
                                .displayname("<color:red>Heart Crown")
                                .build())
                        .build(),

                LCrown.builder()
                        .name("Diamond")
                        .symbol("<color:blue>♦")
                        .itemStack(new ItemBuilder(Material.DIAMOND)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:yellow>Click to equip this crown!")
                                .displayname("<color:blue>Diamond Crown")
                                .build())
                        .build(),

                LCrown.builder()
                        .name("MusicNote")
                        .symbol("<color:green>♪")
                        .itemStack(new ItemBuilder(Material.NOTE_BLOCK)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:yellow>Click to equip this crown!")
                                .displayname("<color:green>Music Note Crown")
                                .build())
                        .build(),

                LCrown.builder()
                        .name("Sun")
                        .symbol("<color:gold>☀")
                        .itemStack(new ItemBuilder(Material.GOLD_INGOT)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:yellow>Click to equip this crown!")
                                .displayname("<color:gold>Sun Crown")
                                .build())
                        .build()
        );
    }

}
