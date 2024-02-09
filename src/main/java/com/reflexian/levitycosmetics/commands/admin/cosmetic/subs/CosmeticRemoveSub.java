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

@SubCommandInfo(name = "remove", command = CosmeticParentCommand.class)
public class CosmeticRemoveSub implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("levitycosmetics.admin.remove")) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return;
        }
        if (args.length == 1) {
            sender.sendMessage("§cPlease specify a player to remove the cosmetic from.");
            return;
        } else if (args.length != 3) {
            sender.sendMessage("§cPlease specify a cosmetic to remove.");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage("§cThe target player was not found.");
            return;
        }

        Cosmetic cosmetic = Cosmetic.getCosmetic(args[2]);
        if (cosmetic == null) {
            sender.sendMessage("§cThe target cosmetic was not found.");
            return;
        }
        UserData userData = UserDataService.shared.retrieveUserFromCache(target.getUniqueId());
        if (userData.getUserCosmetics().stream().noneMatch(e->e.getCosmeticType() == cosmetic.getType())) {
            sender.sendMessage("§cThe target player doesn't have any of those cosmetics.");
            return;
        }

        // remove 1 cosmetic of the type from the user
        userData.getUserCosmetics().stream().filter(e->e.getCosmetic() == cosmetic).findFirst().ifPresent(e->{
            userData.getUserCosmetics().remove(e);
            userData.unequip(e.getCosmetic());
        });
        UserDataService.shared.save(userData, e->{});
        sender.sendMessage("§aSuccessfully removed " + cosmetic.getName() + " from " + target.getName() + ".");
    }
}
