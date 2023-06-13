package com.reflexian.levitycosmetics.commands.admin;

import com.reflexian.levitycosmetics.data.inventories.ChatColorInventory;
import com.reflexian.rapi.api.annotation.CommandInfo;
import com.reflexian.rapi.api.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "test")
public class TestCommand extends Command {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        ChatColorInventory.INVENTORY.open((Player) sender);
        return true;
    }
}
