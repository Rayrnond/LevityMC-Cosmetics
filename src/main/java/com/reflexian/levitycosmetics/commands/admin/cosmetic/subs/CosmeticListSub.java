package com.reflexian.levitycosmetics.commands.admin.cosmetic.subs;

import com.reflexian.levitycosmetics.commands.admin.cosmetic.CosmeticParentCommand;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserCosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.rapi.api.annotation.SubCommandInfo;
import com.reflexian.rapi.api.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;

@SubCommandInfo(name = "list", command = CosmeticParentCommand.class)
public class CosmeticListSub implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("levitycosmetics.admin.list")) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return;
        }
        if (args.length == 1) {

            // filter cosmetics, print in a nice format
            Set<Cosmetic> cosmeticList = Cosmetic.getAllCosmetics();
            sender.sendMessage("§bAll Cosmetics:");
            sender.sendMessage("§b"+cosmeticList.stream().map(Cosmetic::getName).collect(Collectors.joining(", ")));
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage("§cThe target player was not found.");
            return;
        }
        sender.sendMessage("§b"+target.getName()+"§b's Cosmetics:");
        UserData userData = UserDataService.shared.retrieveUserFromCache(target.getUniqueId());
        Set<UserCosmetic> cosmeticList = userData.getUserCosmetics();
        sender.sendMessage(cosmeticList.stream().map(c->c.getCosmetic().getName()).collect(Collectors.joining(", ")));
    }
}
