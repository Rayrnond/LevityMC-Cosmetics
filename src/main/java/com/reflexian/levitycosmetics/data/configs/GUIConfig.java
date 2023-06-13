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
    @ConfigPath("gui.backpack.next-button")
    default ItemStack getBackpackNextItem() {
        ItemBuilder itemBuilder = new ItemBuilder(CustomStack.getInstance("_iainternal:icon_next_orange").getItemStack());
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<#F9C58D>Next Page<#F492F0>");
        itemBuilder.lore("<#F492F0>Click to go to the next page");
        return itemBuilder.build();
    }

    @Comment("The filter option shown in the backpack menu")
    @ConfigPath("gui.backpack.filter-button")
    default ItemStack getBackpackFilterItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.ARROW);
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<#F9C58D>Filter<#F492F0>");
        itemBuilder.lore(" ");
        itemBuilder.lore("%selection1% <#9E9E9E>All Cosmetics");
        itemBuilder.lore("%selection2% <#9E9E9E>Hats");
        itemBuilder.lore("%selection3% <#9E9E9E>Chat Colors");
        itemBuilder.lore("%selection4% <#9E9E9E>Nicknames");
        itemBuilder.lore("%selection5% <#9E9E9E>Titles");
        itemBuilder.lore(" ");
        itemBuilder.lore("<#F492F0>Click to cycle the filter!");
        return itemBuilder.build();
    }

    @Comment("Shown in the backpack GUI as the go back button.")
    @ConfigPath("gui.backpack.back-button")
    default ItemStack getBackpackBackItem() {
        ItemBuilder itemBuilder = new ItemBuilder(CustomStack.getInstance("_iainternal:icon_back_orange").getItemStack());
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<#F9C58D>Previous Page<#F492F0>");
        itemBuilder.lore("<#F492F0>Click to go to the previous page");
        return itemBuilder.build();
    }

    @Comment("Shown in the backpack GUI as the go back button.")
    @ConfigPath("gui.backpack.error")
    default ItemStack getBackpackErrorItem() {
        ItemBuilder itemBuilder = new ItemBuilder(CustomStack.getInstance("_iainternal:icon_cancel").getItemStack());
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<#FF0000>No Items<#8D1338>");
        itemBuilder.lore("<#9E9E9E>You don't have any cosmetics! Purchase some at &nlevitymc.com!");
        return itemBuilder.build();
    }

    @ConfigPath("gui.backpack.back-button.slot")
    default int getBackpackBackItemSlot() {
        return 37;
    }

    @ConfigPath("gui.backpack.next-button.slot")
    default int getBackpackNextItemSlot() {
        return 43;
    }

    @ConfigPath("gui.backpack.error.slot")
    default int getBackpackErrorSlot() {
        return 22;
    }

    @ConfigPath("gui.backpack.filter-button.slot")
    default int getBackpackFilterSlot() {
        return 8;
    }

    @ConfigPath("gui.backpack.filter-button.selected")
    default String getBackpackFilterSelectedIcon() {
        return "<#F2DE68>◉";
    }
    @ConfigPath("gui.backpack.filter-button.not-selected")
    default String getBackpackFilterNotSelectedIcon() {
        return "<#9E9E9E>⦿";
    }

    @Comment("Fills all air pockets in the backpack GUI.")
    @ConfigPath("gui.backpack.filler")
    default ItemStack getBackpackFillerItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE);
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname(" ");
        return itemBuilder.build();
    }


}
