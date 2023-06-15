package com.reflexian.levitycosmetics.commands.admin;

import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.levitycosmetics.utilities.uncategorizied.GradientUtils;
import com.reflexian.rapi.api.annotation.CommandInfo;
import com.reflexian.rapi.api.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

@CommandInfo(name = "test")
public class TestCommand extends Command {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UserData userData = UserDataService.shared.retrieveUserFromCache(player.getUniqueId());
        player.sendMessage("§c" + userData.isTradeBanned());
        player.sendMessage("Cosmetics: " + userData.getAllCosmetics().stream().map(cosmetic -> GradientUtils.colorize(cosmetic.getName())).collect(Collectors.joining(", ")));
        return true;
    }
}
