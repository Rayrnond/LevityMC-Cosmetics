package com.reflexian.levitycosmetics.listeners;

import com.reflexian.levitycosmetics.data.objects.crates.CosmeticCrate;
import com.reflexian.rapi.api.annotation.Registrar;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.stream.Collectors;

@Registrar
public class CrateListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        NBTItem nbtItem = new NBTItem(event.getItemInHand());
        if (nbtItem.hasKey("crate")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getItem().getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(event.getItem());
        if (nbtItem.hasKey("crate")) {
            event.setCancelled(true);
            CosmeticCrate crate = CosmeticCrate.CRATES.getOrDefault(nbtItem.getString("cosmeticCrate"),null);
            if (crate == null) {
                event.getPlayer().sendMessage("§cThat crate does not exist.");
                return;
            }
            if (crate.roll(event.getPlayer())) {
                event.getItem().setAmount(event.getItem().getAmount()-1);
            }
        }
    }
}
