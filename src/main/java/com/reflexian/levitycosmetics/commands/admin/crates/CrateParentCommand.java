package com.reflexian.levitycosmetics.commands.admin.crates;

import com.reflexian.rapi.api.annotation.CommandInfo;
import com.reflexian.rapi.api.command.Command;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "crate")
public class CrateParentCommand extends Command {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("levitycosmetics.admin")) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return true;
        }

        sender.sendMessage("§cPlease specify a subcommand.");
        sender.sendMessage("§cAvailable subcommands: list, get");
        return true;
    }
}
