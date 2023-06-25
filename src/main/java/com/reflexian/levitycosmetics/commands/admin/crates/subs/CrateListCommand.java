package com.reflexian.levitycosmetics.commands.admin.crates.subs;

import com.reflexian.levitycosmetics.commands.admin.crates.CrateParentCommand;
import com.reflexian.levitycosmetics.data.objects.crates.CosmeticCrate;
import com.reflexian.rapi.api.annotation.SubCommandInfo;
import com.reflexian.rapi.api.command.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.stream.Collectors;

@SubCommandInfo(name = "list", command = CrateParentCommand.class)
public class CrateListCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("levitycosmetics.admin.crate.list")) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return;
        }

        Collection<CosmeticCrate> crates = CosmeticCrate.CRATES.values();
        sender.sendMessage("§eAll Crates:");
        sender.sendMessage("§e"+crates.stream().map(CosmeticCrate::getName).collect(Collectors.joining(", ")));
        return;

    }
}
