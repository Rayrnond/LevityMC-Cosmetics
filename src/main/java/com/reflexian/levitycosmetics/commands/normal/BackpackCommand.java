package com.reflexian.levitycosmetics.commands.normal;

import com.reflexian.levitycosmetics.data.inventories.BackpackInventory;
import com.reflexian.levitycosmetics.data.inventories.ChatColorInventory;
import com.reflexian.rapi.api.annotation.CommandInfo;
import com.reflexian.rapi.api.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "backpack")
public class BackpackCommand extends Command {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        BackpackInventory.INVENTORY.open((Player) sender);
        return true;
    }
}
