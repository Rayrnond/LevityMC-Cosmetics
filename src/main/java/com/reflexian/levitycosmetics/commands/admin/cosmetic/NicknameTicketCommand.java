package com.reflexian.levitycosmetics.commands.admin.cosmetic;


import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.utilities.uncategorizied.GradientUtils;
import com.reflexian.rapi.api.annotation.CommandInfo;
import com.reflexian.rapi.api.command.Command;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "nicknameticket")
public class NicknameTicketCommand extends Command {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("levitycosmetics.admin.ticket")) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        Player player = (Player) sender;

        Player target = args.length == 0 ? player : player.getServer().getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("§cThat player could not be found!");
            return true;
        }


        NBTItem nbtItem = new NBTItem(LevityCosmetics.getInstance().getDefaultConfig().getNicknameTicket());
        nbtItem.setBoolean("nicknameticket", true);
        target.getInventory().addItem(nbtItem.getItem());
        player.sendMessage(GradientUtils.colorize(LevityCosmetics.getInstance().getMessagesConfig().getNicknameGivenMessage().replace("%player%", args.length == 0 ? player.getName() : args[0])));
        return true;
    }
}
