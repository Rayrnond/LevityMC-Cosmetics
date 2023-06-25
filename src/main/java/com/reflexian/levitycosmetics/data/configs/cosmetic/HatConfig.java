package com.reflexian.levitycosmetics.data.configs.cosmetic;

import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LHat;
import com.reflexian.levitycosmetics.utilities.uncategorizied.ItemBuilder;
import org.bukkit.Material;
import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.Comment;
import pl.mikigal.config.annotation.ConfigName;
import pl.mikigal.config.annotation.ConfigPath;

import java.util.Arrays;
import java.util.List;

@ConfigName("hats.yml")
public interface HatConfig extends Config {



    @ConfigPath("list")
    @Comment("""
            # This is a list of hats cosmetics. Each cosmetic in the plugin has a unique id, which is stored in the database.
            # Do not change the id unless you know what you are doing!
            #
            # name: identifier for the hat cosmetic, no spaces.
            # hat: The item that will be displayed on the player's head.
            # itemStack: itemstack of the cosmetic in GUI
            #
            # OPTIONALS:
            # chatcolor: ID of cosmetic assigned chatcolor
            # tabcolor: ID of cosmetic assigned chatcolor
            # glow: ID of cosmetic assigned chatcolor
            # crown: ID of cosmetic assigned chatcolor
            #
            
            """)
    default List<LHat> getHats() {
        return Arrays.asList(
                LHat.builder()
                        .name("TNTHat")
                        .chatColor("EtherealGradientChatColor")
                        .glow("AquaGlow")
                        .crown("Money")
                        .itemStack(new ItemBuilder(Material.TNT)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:gray> > <color:yellow>Aqua Glow","<color:gray> > <color:yellow>EtherealGradient Chat Color","<color:gray> > <color:yellow>Money Crown"," ", "<color:yellow>Click to equip this hat!")
                                .displayname("<color:red>TNT Hat")
                                    .build())
                        .helmet(new ItemBuilder(Material.TNT)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .lore("<color:gray>You are basically a creeper!")
                                .displayname("<color:red>TNT Hat")
                                .build())
                        .build()
        );
    }

}
