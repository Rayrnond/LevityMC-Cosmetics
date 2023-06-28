package com.reflexian.levitycosmetics.commands.admin;

import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.AssignedHat;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.rapi.api.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//@CommandInfo(name = "check")
public class CheckCommand extends Command {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UserData userData = UserDataService.shared.retrieveUserFromCache(player.getUniqueId());
        for (AssignedHat assignedHat : userData.getAssignedHats()) {
            player.sendMessage("Hat: " + assignedHat.getCosmeticId() + " " + assignedHat.getName());
            player.getInventory().addItem(assignedHat.getItemStack());
        }
        return true;
    }
}
