package com.reflexian.levitycosmetics.data.configs.cosmetic;

import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.LTitle;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.LTitlePaint;
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
                        .name("LavaTitle")
                        .tag("<gradient:#F5C9C9:#B8A4C9>Lava</gradient>")
                        .itemStack(new ItemBuilder(Material.GOLDEN_CARROT)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .displayname("<gradient:#F5C9C9:#B8A4C9>Lava Title</gradient>")
                                .build())
                        .build(),

                LTitle.builder()
                        .name("PurpleMistTitle")
                        .tag("<gradient:#6A11A5:#E707FA>PurpleMist</gradient>")
                        .itemStack(new ItemBuilder(Material.ALLAY_SPAWN_EGG)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .displayname("<gradient:#6A11A5:#E707FA>PurpleMist Title</gradient>")
                                .build())
                        .build()
        );
    }


    @ConfigPath("paints")
    @Comment("""
            # This is a list of title paint cosmetics. Each cosmetic in the plugin has a unique id, which is stored in the database.
            # Do not change the id unless you know what you are doing!
            #
            # name: identifier for the cosmetic, no spaces.
            # color: color of the cosmetic. Keep %tag% in, which is replaced with the tag. 
            # itemStack: itemstack of the cosmetic in GUI
            
            """)
    default List<LTitlePaint> getTitlePaints() {
        return Arrays.asList(
                LTitlePaint.builder()
                        .name("LavaTagColor")
                        .color("<gradient:#F5C9C9:#B8A4C9>%tag%</gradient>")
                        .itemStack(new ItemBuilder(Material.EMERALD)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .displayname("<gradient:#F5C9C9:#B8A4C9>Lava Title Paint</gradient>")
                                .build())
                        .build(),

                LTitlePaint.builder()
                        .name("PurpleMistTagColor")
                        .color("<gradient:#6A11A5:#E707FA>%tag%</gradient>")
                        .itemStack(new ItemBuilder(Material.DIAMOND)
                                .replaceAndSymbol(false)
                                .amount(1)
                                .displayname("<gradient:#6A11A5:#E707FA>PurpleMist Title Paint</gradient>")
                                .build())
                        .build()
        );
    }
}
