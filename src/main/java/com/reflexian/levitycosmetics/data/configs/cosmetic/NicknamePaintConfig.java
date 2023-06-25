package com.reflexian.levitycosmetics.data.configs.cosmetic;

import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LTabColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.nickname.LNicknamePaint;
import com.reflexian.levitycosmetics.utilities.uncategorizied.ItemBuilder;
import org.bukkit.Material;
import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.Comment;
import pl.mikigal.config.annotation.ConfigName;
import pl.mikigal.config.annotation.ConfigPath;

import java.util.Collections;
import java.util.List;

@ConfigName("nicknamepaint.yml")
public interface NicknamePaintConfig extends Config {



    @ConfigPath("list")
    @Comment("""
            # This is a list of nickname color cosmetics. Each cosmetic in the plugin has a unique id, which is stored in the database.
            # Do not change the id unless you know what you are doing!
            #
            # name: identifier for the cosmetic, no spaces.
            # color: The color of the player's name, always include %player% which is replaced with the player's name. 
            # itemStack: itemstack of the cosmetic in GUI
            
            """)
    default List<LNicknamePaint> getNicknamePaints() {
        return Collections.singletonList(
                LNicknamePaint.builder()
                        .name("OceanNicknamePaint")
                        .color("<gradient:#0061ff:#60efff>%player%</gradient>")
                        .itemStack(new ItemBuilder(Material.WATER_BUCKET)
                                .replaceAndSymbol(false) // internally required
                                .amount(1)
                                .lore("<color:yellow>Click to equip this nickname paint!")
                                .displayname("<gradient:#0061ff:#60efff>Ocean Nickname Paint</gradient>")
                                .build())
                        .build()
        );
    }

}
