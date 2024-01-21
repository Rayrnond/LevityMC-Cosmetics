package com.reflexian.levitycosmetics.data.configs;

import com.reflexian.levitycosmetics.utilities.uncategorizied.ItemBuilder;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.Comment;
import pl.mikigal.config.annotation.ConfigName;
import pl.mikigal.config.annotation.ConfigPath;

import java.util.ArrayList;
import java.util.List;

@ConfigName("gui.yml")
public interface GUIConfig extends Config {


    @ConfigPath("gui.backpack.title")
    @Comment("Shown in the backpack GUI as the title.")
    default String getBackpackTitle() {
        return "<gradient:#F9C58D:#F492F0>My Backpack</gradient>";
    }


    @Comment("Additional lore in drop menu.")
    @ConfigPath("gui.drop.lore")
    default List<String> getDropItemLore() {
        List<String> itemBuilder = new ArrayList<>();
        itemBuilder.add("<color:gray>Current Cost: %cost% credits");
        itemBuilder.add("<color:gray>Amount in stock: x%stock%");
        itemBuilder.add("<color:gray>Cosmetic: %cosmetic%");
        itemBuilder.add(" ");
        itemBuilder.add("<color:gray>Click to purchase!");
        return itemBuilder;
    }

    @Comment("Shown in the backpack GUI as the go to next page button.")
    @ConfigPath("gui.backpack.next-button")
    default ItemStack getBackpackNextItem() {
        ItemBuilder itemBuilder = new ItemBuilder(CustomStack.getInstance("_iainternal:icon_next_orange").getItemStack());
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<gradient:#F9C58D:#F492F0>Next Page</gradient>");
        itemBuilder.lore("<color:gray>Click to go to the next page");
        return itemBuilder.build();
    }


    @Comment("Shown in the backpack GUI as the close button")
    @ConfigPath("gui.backpack.close-button")
    default ItemStack getBackpackCloseButtonItem() {
        ItemBuilder itemBuilder = new ItemBuilder(CustomStack.getInstance("_iainternal:icon_next_orange").getItemStack());
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<color:red>Close</gradient>");
        itemBuilder.lore("<color:gray>Click to close this menu");
        return itemBuilder.build();
    }

    @Comment("The filter option shown in the backpack menu")
    @ConfigPath("gui.backpack.filter-button")
    default ItemStack getBackpackFilterItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.ARROW);
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<gradient:#F9C58D:#F492F0>Filter</gradient>");
        itemBuilder.lore(" ");
        itemBuilder.lore("%selection1% <color:gray>All Cosmetics");
//        itemBuilder.lore("%selection2% <color:gray>Hats");
        itemBuilder.lore("%selection2% <color:gray>Chat Colors");
        itemBuilder.lore("%selection3% <color:gray>Nicknames");
        itemBuilder.lore("%selection4% <color:gray>Titles");
        itemBuilder.lore("%selection5% <color:gray>Tab Color");
        itemBuilder.lore("%selection6% <color:gray>Sorted by rarities");

        return itemBuilder.build();
    }

    @Comment("Shown in the backpack GUI as the go back button.")
    @ConfigPath("gui.backpack.back-button")
    default ItemStack getBackpackBackItem() {
        ItemBuilder itemBuilder = new ItemBuilder(CustomStack.getInstance("_iainternal:icon_back_orange").getItemStack());
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<gradient:#F9C58D:#F492F0>Previous Page</gradient>");
        itemBuilder.lore("<color:gray>Click to go to the previous page");
        return itemBuilder.build();
    }

    @Comment("Shown in the backpack GUI as the go back button.")
    @ConfigPath("gui.backpack.error")
    default ItemStack getBackpackErrorItem() {
        ItemBuilder itemBuilder = new ItemBuilder(CustomStack.getInstance("_iainternal:icon_cancel").getItemStack());
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<color:red>No Cosmetics");
        itemBuilder.lore("<color:gray>This category doesn't have any cosmetics!");
        return itemBuilder.build();
    }

    @Comment("Shown in the backpack GUI as the reset button.")
    @ConfigPath("gui.backpack.reset-button")
    default ItemStack getBackpackResetItem() {
        ItemBuilder itemBuilder = new ItemBuilder(CustomStack.getInstance("_iainternal:icon_cancel").getItemStack());
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<color:red>Reset Cosmetics");
        itemBuilder.lore("<color:gray>Unequip all of your cosmetics!");
        return itemBuilder.build();
    }

    @Comment("Shown in the backpack GUI as purchase next page button.")
    @ConfigPath("gui.backpack.purchase-page-button")
    default ItemStack getBackpackPurchasePageButton() {
        ItemBuilder itemBuilder = new ItemBuilder(CustomStack.getInstance("_iainternal:icon_cancel").getItemStack());
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<color:red>Purchase additional page");
        itemBuilder.lore("<color:gray>Click to buy a new page for 1,000 credits!");
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

    @ConfigPath("gui.backpack.close-button.slot")
    default int getBackpackCloseButtonSlot() {
        return 40;
    }



    @ConfigPath("gui.backpack.error.slot")
    default int getBackpackErrorSlot() {
        return 22;
    }

    @ConfigPath("gui.backpack.filter-button.slot")
    default int getBackpackFilterSlot() {
        return 8;
    }
    @ConfigPath("gui.backpack.reset-button.slot")
    default int getBackpackResetSlot() {
        return 7;
    }

    @ConfigPath("gui.backpack.filter-button.selected")
    default String getBackpackFilterSelectedIcon() {
        return "<color:yellow>◉";
    }
    @ConfigPath("gui.backpack.filter-button.not-selected")
    default String getBackpackFilterNotSelectedIcon() {
        return "<color:gray>◉";
    }

    @Comment("Fills all air pockets in the backpack GUI.")
    @ConfigPath("gui.backpack.filler")
    default ItemStack getBackpackFillerItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE);
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname(" ");
        return itemBuilder.build();
    }

    @Comment("This is the item shown in backpack")
    @ConfigPath("items.nickname.backpack")
    default ItemStack getNicknameCosmeticBackpackItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.NAME_TAG);
        itemBuilder.replaceAndSymbol(false);
        itemBuilder.displayname("<color:red>Nickname: %nickname%");
        itemBuilder.lore("<color:gray>Click to select this nickname!");
        return itemBuilder.build();
    }

}
