package com.reflexian.levitycosmetics.commands.admin.drop.subs;

import com.reflexian.levitycosmetics.commands.admin.drop.DropParentCommand;
import com.reflexian.levitycosmetics.commands.admin.drop.helpers.DropEvent;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
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
        int credits = 1000; // todo get player's credits
        UserData userData = UserDataService.shared.retrieveUserFromCache(player.getUniqueId());
        if (event.getAlreadyPurchased().contains(player.getUniqueId())) {
            sender.sendMessage("§cYou have already purchased this drop.");
            return;
        } else if (event.getCurrentAmount() == 0 || event.getCurrentCost() == 0) {
            sender.sendMessage("§cThat drop is no longer available.");
            return;
        }
        if (userData.getCredits() < event.getCurrentCost()) {
            sender.sendMessage("§cYou do not have enough credits to purchase this drop.");
            return;
        }
        userData.removeCredits(event.getCurrentCost());
        event.getAlreadyPurchased().add(player.getUniqueId());
        event.setCurrentAmount(event.getCurrentAmount() - 1);

        sender.sendMessage("§aYou have purchased this drop for §e" + event.getCurrentCost() + " §acredits.");
        if (event.isCrate()) {
            player.getInventory().addItem(event.getCosmeticCrate().getItemStack());
            player.sendMessage("§aYou have been given a " + event.getCosmeticCrate().getName() + " crate.");
        } else {
            event.getCosmetic().giveToUser(userData);
            player.sendMessage("§aYou have been given " + event.getCosmetic().getName() + " cosmetic.");
        }

    }
}
