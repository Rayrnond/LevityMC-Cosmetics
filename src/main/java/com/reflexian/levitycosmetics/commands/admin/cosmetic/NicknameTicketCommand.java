package com.reflexian.levitycosmetics.commands.admin.cosmetic;


import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.Database;
import com.reflexian.levitycosmetics.data.objects.cosmetics.nickname.AssignedNickname;
import com.reflexian.levitycosmetics.data.objects.user.UserCosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.levitycosmetics.utilities.uncategorizied.GradientUtils;
import com.reflexian.rapi.api.annotation.CommandInfo;
import com.reflexian.rapi.api.command.Command;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

@CommandInfo(name = "nicknameticket")
public class NicknameTicketCommand extends Command {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("levitycosmetics.admin.ticket")) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        if (sender instanceof ConsoleCommandSender) {


            if (args.length == 0) {
                sender.sendMessage("§cPlease specify a player to give the ticket to.");
                return true;
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage("§cThat player could not be found!");
                return true;
            }

            final String id = new Random().nextInt(3293)+target.getUniqueId().toString().substring(0,10).replace("-","");

            UserData user = UserDataService.shared.retrieveUserFromCache(target.getUniqueId());
            AssignedNickname assignedNickname = new AssignedNickname(target.getUniqueId(), id, "", null);
            user.getAssignedNicknames().add(assignedNickname);
            user.getUserCosmetics().add(UserCosmetic.fromCosmetic(user.getUuid(), assignedNickname));
            Database.shared.save(assignedNickname);
            sender.sendMessage(GradientUtils.colorize(LevityCosmetics.getInstance().getMessagesConfig().getNicknameGivenMessage().replace("%player%", args[0])));


            return true;
        }

        Player player = (Player) sender;

        Player target = args.length == 0 ? player : player.getServer().getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("§cThat player could not be found!");
            return true;
        }

        final String id = new Random().nextInt(3293)+player.getUniqueId().toString().substring(0,10).replace("-","");

        UserData user = UserDataService.shared.retrieveUserFromCache(target.getUniqueId());
        AssignedNickname assignedNickname = new AssignedNickname(target.getUniqueId(), id, "", null);
        user.getAssignedNicknames().add(assignedNickname);
        user.getUserCosmetics().add(UserCosmetic.fromCosmetic(user.getUuid(), assignedNickname));
        Database.shared.save(assignedNickname);
        player.sendMessage(GradientUtils.colorize(LevityCosmetics.getInstance().getMessagesConfig().getNicknameGivenMessage().replace("%player%", args.length == 0 ? player.getName() : args[0])));
        return true;
    }
}
