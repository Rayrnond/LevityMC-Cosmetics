package com.reflexian.levitycosmetics.data.inventories;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.commands.admin.drop.DropParentCommand;
import com.reflexian.levitycosmetics.commands.admin.drop.helpers.DropEvent;
import com.reflexian.levitycosmetics.data.configs.ConfigurationLoader;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.levitycosmetics.utilities.uncategorizied.InvUtils;
import com.reflexian.levitycosmetics.utilities.uncategorizied.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DropInventory implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("dropInventory")
            .provider(new DropInventory())
            .size(3, 9)
            .title("Drop")
            .manager(LevityCosmetics.getInstance().getInventoryManager())
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fill(ClickableItem.empty(ConfigurationLoader.GUI_CONFIG.getBackpackFillerItem()));

    }

    @Override
    public void update(Player player, InventoryContents contents) {

        DropEvent event = DropParentCommand.EVENT;
        if (event == null) {
            player.closeInventory();
            return;
        }

        ItemStack itemStack = event.isCrate() ? event.getCosmeticCrate().getItemStack().clone() : event.getCosmetic().getItemStack().clone();

        for (String s : ConfigurationLoader.GUI_CONFIG.getDropItemLore()) {
            ItemBuilder.addLore(itemStack, s.replace("%cost%", String.valueOf(event.getCurrentCost())).replace("%stock%", String.valueOf(event.getCurrentAmount())).replace("%cosmetic%", event.isCrate() ? event.getCosmeticCrate().getName() : event.getCosmetic().getName()));
        }


        contents.set(InvUtils.getPos(13), ClickableItem.of(itemStack, e -> {
            UserData userData = UserDataService.shared.retrieveUserFromCache(player.getUniqueId());

             if (event.getCurrentAmount() == 0 || event.getCurrentCost() == 0) {
                player.sendMessage("§cThat drop is no longer available.");
                player.closeInventory();
                return;
            } else if (event.getAlreadyPurchased().contains(player.getUniqueId())) {
                player.sendMessage("§cYou have already purchased this drop.");
                player.closeInventory();
                return;
            }else if (userData.getCredits() < event.getCurrentCost()) {
                player.sendMessage("§cYou do not have enough credits to purchase this drop.");
                player.closeInventory();
                return;
            }
            userData.removeCredits(event.getCurrentCost());
            event.getAlreadyPurchased().add(player.getUniqueId());
            event.setCurrentAmount(event.getCurrentAmount() - 1);

            player.sendMessage("§aYou have purchased this drop for §e" + event.getCurrentCost() + " §acredits.");
            player.closeInventory();
            if (event.isCrate()) {
                player.getInventory().addItem(event.getCosmeticCrate().getItemStack());
                player.sendMessage("§aYou have been given a " + event.getCosmeticCrate().getName() + " crate.");
            } else {
                event.getCosmetic().giveToUser(userData);
                player.sendMessage("§aYou have been given " + event.getCosmetic().getName() + " cosmetic.");
            }

        }));
    }
}
