package com.reflexian.levitycosmetics.data.configs.cosmetic;

import com.reflexian.levitycosmetics.data.objects.crates.CosmeticCrate;
import com.reflexian.levitycosmetics.utilities.uncategorizied.ItemBuilder;
import org.bukkit.Material;
import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.Comment;
import pl.mikigal.config.annotation.ConfigName;
import pl.mikigal.config.annotation.ConfigPath;

import java.util.Arrays;
import java.util.List;

@ConfigName("crates.yml")
public interface CrateConfig extends Config {



    @ConfigPath("list")
    @Comment("""
            # This is a list of crates.
            # Do not change the id unless you know what you are doing!
            #
            # name: identifier for the cosmetic, no spaces.
            # itemStack: itemstack of the cosmetic
            
            """)
    default List<CosmeticCrate> getCrates() {
        return Arrays.asList(
                CosmeticCrate.builder()
                        .name("Tier1")
                        .potentialCosmetics(Arrays.asList("FireGradientChatColor", "LavaTitle"))
                        .itemstack(new ItemBuilder(Material.CHEST)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:yellow>Right Click to open!")
                                .displayname("<color:gray><bold>Levity Crate Tier 1</bold>")
                                .build())
                        .build()
                ,
                CosmeticCrate.builder()
                        .name("Tier2")
                        .potentialCosmetics(Arrays.asList("OceanGradientChatColor", "LavaTitle", "PurpleMistTitle"))
                        .itemstack(new ItemBuilder(Material.ENDER_CHEST)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:yellow>Contains cool cosmetics!!!")
                                .displayname("<color:yellow><bold>Levity Crate Tier 2</bold>")
                                .build())
                        .build()
        );
    }

}
