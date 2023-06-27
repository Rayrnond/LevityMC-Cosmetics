package com.reflexian.levitycosmetics.commands.admin.drop;

import com.reflexian.levitycosmetics.commands.admin.drop.helpers.DropEvent;
import com.reflexian.levitycosmetics.data.inventories.DropInventory;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.crates.CosmeticCrate;
import com.reflexian.rapi.api.annotation.CommandInfo;
import com.reflexian.rapi.api.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "drop")
public class DropParentCommand extends Command {

    public static DropEvent EVENT = null;

    @Override
    public boolean execute(CommandSender sender, String[] args) {


        if (args.length == 0) {
            if (EVENT == null) {
                sender.sendMessage("§cThere is no drop event running!");
            } else {
                DropInventory.INVENTORY.open((Player) sender);
            }
            return true;
        }

        if (!sender.hasPermission("levitycosmetics.admin.drop")) {
            sender.sendMessage("§cYou do not have permission to use this command!");
            return true;
        }

        if (args.length != 3) {
            sender.sendMessage("§cUsage: /drop <cosmetic or crate> <amount> <starting cost>");
            return true;
        }

        if (EVENT != null) {
            sender.sendMessage("§cThere is already a drop event running! Use /drop cancel to cancel.");
            return true;
        }

        try {
            if (Integer.parseInt(args[1]) < 1) {
                sender.sendMessage("§cAmount must be greater than 0!");
                return true;
            }
            if (Integer.parseInt(args[2]) < 1) {
                sender.sendMessage("§cStarting cost must be greater than 0!");
                return true;
            }

            if (Cosmetic.getCosmetic(args[0]) != null) {

                EVENT = new DropEvent(Cosmetic.getCosmetic(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));

            } else if (CosmeticCrate.CRATES.getOrDefault(args[0], null) != null) {
                EVENT = new DropEvent(CosmeticCrate.CRATES.getOrDefault(args[0], null), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            } else {
                sender.sendMessage("§cThat cosmetic or crate does not exist!");
                return true;
            }

            EVENT.runTaskTimer(Bukkit.getPluginManager().getPlugin("LevityCosmetics"), 0, 20*10);
        } catch (Exception e) {
            sender.sendMessage("§7Error: " + e.getLocalizedMessage());
            sender.sendMessage("§cUsage: /drop <cosmetic or crate> <amount> <starting cost>");
            return true;
        }

        return true;
    }


}
