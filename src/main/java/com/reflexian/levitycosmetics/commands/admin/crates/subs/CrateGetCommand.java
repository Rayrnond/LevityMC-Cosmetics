package com.reflexian.levitycosmetics.commands.admin.crates.subs;

import com.reflexian.levitycosmetics.commands.admin.crates.CrateParentCommand;
import com.reflexian.levitycosmetics.data.objects.crates.CosmeticCrate;
import com.reflexian.rapi.api.annotation.SubCommandInfo;
import com.reflexian.rapi.api.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommandInfo(name = "get", command = CrateParentCommand.class)
public class CrateGetCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("levitycosmetics.admin.crate.get")) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return;
        }
        if (args.length == 1) {
            sender.sendMessage("§cPlease specify a crate.");
            return;
        }
        Player target = args.length == 3 ? sender.getServer().getPlayer(args[2]) : (sender instanceof Player ? (Player) sender : null);
        String crate = args[1];
        CosmeticCrate cosmeticCrate = CosmeticCrate.CRATES.getOrDefault(crate,null);
        if (cosmeticCrate == null) {
            sender.sendMessage("§cThat crate does not exist.");
            return;
        }
        if (target == null) {
            sender.sendMessage("§cThat player is not online.");
            return;
        }
        target.getInventory().addItem(cosmeticCrate.getItemStack());
        sender.sendMessage("§aYou have given " + target.getName() + " a " + cosmeticCrate.getName() + " crate.");

    }
}
