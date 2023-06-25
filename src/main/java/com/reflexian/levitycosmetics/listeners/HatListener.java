package com.reflexian.levitycosmetics.listeners;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.rapi.api.annotation.Registrar;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Registrar
public class HatListener implements Listener {

    @EventHandler
    public void onClick(PlayerArmorChangeEvent e) {
        if (e.getOldItem() != null && !e.getOldItem().getType().equals(Material.AIR)) {
            NBTItem nbtItem = new NBTItem(e.getOldItem());
            if (nbtItem.hasKey("levitycosmeticshelmet")) {
                e.getOldItem().setAmount(0);
                UserData userData = UserDataService.shared.retrieveUserFromCache(e.getPlayer().getUniqueId());
                userData.getSelectedHat().unequipHat(userData);
                e.getPlayer().sendMessage("§cYou have unequipped your hat.");
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null || !clickedInventory.equals(event.getWhoClicked().getInventory())) {
            return;
        }

        int clickedSlot = event.getSlot();

        if (clickedSlot == 39) {

            ItemStack itemStack = clickedInventory.getItem(clickedSlot);
            if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
                return;
            }
            NBTItem nbtItem = new NBTItem(itemStack);
            if (nbtItem.hasKey("levitycosmeticshelmet")) {
                event.setCancelled(true);
                itemStack.setAmount(0);
                UserData userData = UserDataService.shared.retrieveUserFromCache(event.getWhoClicked().getUniqueId());
                userData.getSelectedHat().unequipHat(userData);
                event.getWhoClicked().sendMessage("§cYou have unequipped your hat.");
            }
        }
    }
}
