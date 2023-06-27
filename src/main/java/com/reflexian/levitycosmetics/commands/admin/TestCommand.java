package com.reflexian.levitycosmetics.commands.admin;

import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.rapi.api.annotation.CommandInfo;
import com.reflexian.rapi.api.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//@CommandInfo(name = "test")
public class TestCommand extends Command {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        final UserData userData = UserDataService.shared.retrieveUserFromCache(player.getUniqueId());
        for (Cosmetic a : Cosmetic.getAllCosmetics()) {
//            if (userData.getAllCosmetics().contains(a)) continue;
            a.giveToUser(userData);
        }
        player.sendMessage("§aYou now have all cosmetics! Keep in mind these are placeholders and can be added to in configs.");
        return true;
    }
}
