package com.reflexian.levitycosmetics.commands.admin.drop.subs;

import com.reflexian.levitycosmetics.commands.admin.drop.DropParentCommand;
import com.reflexian.levitycosmetics.commands.admin.drop.helpers.DropEvent;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.rapi.api.annotation.SubCommandInfo;
import com.reflexian.rapi.api.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommandInfo(name = "cancel", command = DropParentCommand.class)
public class DropCancelSub implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (!sender.hasPermission("levitycosmetics.admin.drop")) {
            sender.sendMessage("§cYou do not have permission to use this command!");
            return;
        }
        DropEvent event = DropParentCommand.EVENT;
        if (event == null) {
            sender.sendMessage("§cThere is no drop event currently active.");
            return;
        }
        event.cancel();
        DropParentCommand.EVENT = null;
        sender.sendMessage("§aYou have cancelled the drop event.");
        return;

    }
}
