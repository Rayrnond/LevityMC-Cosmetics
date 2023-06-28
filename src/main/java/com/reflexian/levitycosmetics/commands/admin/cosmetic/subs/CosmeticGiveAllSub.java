package com.reflexian.levitycosmetics.commands.admin.cosmetic.subs;

import com.reflexian.levitycosmetics.commands.admin.cosmetic.CosmeticParentCommand;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.rapi.api.annotation.SubCommandInfo;
import com.reflexian.rapi.api.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommandInfo(name = "giveall", command = CosmeticParentCommand.class)
public class CosmeticGiveAllSub implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("levitycosmetics.admin.giveall")) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return;
        }
        if (args.length != 2) {
            sender.sendMessage("§cPlease specify a player to give the cosmetic to.");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage("§cThe target player was not found.");
            return;
        }

        UserData userData = UserDataService.shared.retrieveUserFromCache(target.getUniqueId());
        for (Cosmetic allCosmetic : Cosmetic.getAllCosmetics()) {
            allCosmetic.giveToUser(userData);
        }
        UserDataService.shared.save(userData, e->{});
        sender.sendMessage("§aSuccessfully gave " + Cosmetic.getAllCosmetics().size() + " cosmetics to " + target.getName() + ".");
    }


}
