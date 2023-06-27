package com.reflexian.levitycosmetics.commands.admin.drop.subs;

import com.reflexian.levitycosmetics.commands.admin.drop.DropParentCommand;
import com.reflexian.levitycosmetics.commands.admin.drop.helpers.DropEvent;
import com.reflexian.levitycosmetics.data.inventories.DropInventory;
import com.reflexian.rapi.api.annotation.SubCommandInfo;
import com.reflexian.rapi.api.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommandInfo(name = "purchase", command = DropParentCommand.class)
public class DropPurchaseSub implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        DropEvent event = DropParentCommand.EVENT;
        if (event == null) {
            sender.sendMessage("§cThere is no drop event currently active.");
            return;
        }
        DropInventory.INVENTORY.open(player);

    }
}
