package com.reflexian.levitycosmetics.commands.admin.cosmetic.subs;

import com.reflexian.levitycosmetics.commands.admin.cosmetic.CosmeticParentCommand;
import com.reflexian.levitycosmetics.data.Database;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.rapi.api.annotation.SubCommandInfo;
import com.reflexian.rapi.api.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

@SubCommandInfo(name = "reset", command = CosmeticParentCommand.class)
public class CosmeticResetSub implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("levitycosmetics.admin.reset")) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return;
        }
        if (args.length == 1) {
            sender.sendMessage("§cPlease specify a player to reset.");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage("§cThe target player was not found.");
            return;
        }

        UserData userData = UserDataService.shared.retrieveUserFromCache(target.getUniqueId());
        userData.reset();
        UserDataService.shared.save(userData, e->{});

        try {
            Database.shared.getConnection().prepareStatement("DELETE FROM `titles` WHERE `user_id` = '" + target.getUniqueId().toString() + "';").executeUpdate();
            Database.shared.getConnection().prepareStatement("DELETE FROM `nicknames` WHERE `user_id` = '" + target.getUniqueId().toString() + "';").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            sender.sendMessage("§cAn error occurred while resetting the player.");
            return;
        }
        sender.sendMessage("§aSuccessfully reset " + target.getName() + ".");
    }
}
