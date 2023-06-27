package com.reflexian.levitycosmetics.commands.admin;

import com.reflexian.levitycosmetics.data.objects.user.UserCosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.rapi.api.annotation.CommandInfo;
import com.reflexian.rapi.api.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//@CommandInfo(name = "untest")
public class UntestCommand extends Command {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        final UserData userData = UserDataService.shared.retrieveUserFromCache(player.getUniqueId());
//        userData.getUserCosmetics().stream().map(UserCosmetic::getCosmetic).forEach(cosmetic -> cosmetic.removeFromUser(userData));
        player.sendMessage("§cYou now have 0 cosmetics!");
        return true;
    }
}
