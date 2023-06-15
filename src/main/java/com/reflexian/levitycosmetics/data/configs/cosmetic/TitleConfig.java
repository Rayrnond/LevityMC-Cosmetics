package com.reflexian.levitycosmetics.data.configs.cosmetic;

import com.reflexian.levitycosmetics.data.objects.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.titles.LTitle;
import com.reflexian.levitycosmetics.utilities.uncategorizied.ItemBuilder;
import org.bukkit.Material;
import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.Comment;
import pl.mikigal.config.annotation.ConfigName;
import pl.mikigal.config.annotation.ConfigPath;

import java.util.Arrays;
import java.util.List;

@ConfigName("titles.yml")
public interface TitleConfig extends Config {



    @ConfigPath("list")
    @Comment("""
            # This is a list of title cosmetics. Each cosmetic in the plugin has a unique id, which is stored in the database.
            # Do not change the id unless you know what you are doing!
            #
            # id: identifier for the cosmetic.
            # name: name of the cosmetic. 
            # itemStack: itemstack of the cosmetic in GUI
            
            """)
    default List<LTitle> getTitles() {
        return Arrays.asList(
                LTitle.builder()
                        .id("0")
                        .name("<#F5C9C9>Lava<#B8A4C9> Title")
                        .tag("<#F5C9C9>Lava<#B8A4C9>")
                        .itemStack(new ItemBuilder(Material.GOLDEN_CARROT)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .displayname("<#F5C9C9>Lava<#B8A4C9> Title")
                                .build())
                        .build(),

                LTitle.builder()
                        .id("1")
                        .name("<#6A11A5>PurpleMist<#E707FA> Title")
                        .tag("<#6A11A5>PurpleMist<#E707FA>")
                        .itemStack(new ItemBuilder(Material.ALLAY_SPAWN_EGG)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .displayname("<#6A11A5>PurpleMist<#E707FA> Title")
                                .build())
                        .build()
        );
    }

}
