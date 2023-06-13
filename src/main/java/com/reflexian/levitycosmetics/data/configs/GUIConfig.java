package com.reflexian.levitycosmetics.data.configs;

import com.reflexian.levitycosmetics.utilities.ItemBuilder;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.Comment;
import pl.mikigal.config.annotation.ConfigName;
import pl.mikigal.config.annotation.ConfigPath;

@ConfigName("gui.yml")
public interface GUIConfig extends Config {


    @ConfigPath("gui.backpack.title")
    @Comment("Shown in the backpack GUI as the title.")
    default String getBackpackTitle() {
        return "<#F9C58D>My Backpack<#F492F0>";
    }

    @Comment("Shown in the backpack GUI as the go to next page button.")
    @ConfigPath("gui.next-button")
    default ItemStack getGUINext() {
        ItemBuilder itemBuilder = new ItemBuilder(CustomStack.getInstance("_iainternal:icon_next_orange").getItemStack());
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<#F9C58D>Next Page<#F492F0>");
        itemBuilder.lore("<#F492F0>Click to go to the next page");
        return itemBuilder.build();
    }

    @Comment("Shown in the backpack GUI as the go back button.")
    @ConfigPath("gui.back-button")
    default ItemStack getGUIBack() {
        ItemBuilder itemBuilder = new ItemBuilder(CustomStack.getInstance("_iainternal:icon_back_orange").getItemStack());
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<#F9C58D>Previous Page<#F492F0>");
        itemBuilder.lore("<#F492F0>Click to go to the previous page");
        return itemBuilder.build();
    }

    @Comment("Fills all air pockets in the backpack GUI.")
    @ConfigPath("gui.filler")
    default ItemStack getGUIFiller() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE);
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname(" ");
        return itemBuilder.build();
    }


}
