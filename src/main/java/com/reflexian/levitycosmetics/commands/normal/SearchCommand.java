package com.reflexian.levitycosmetics.commands.normal;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.configs.ConfigurationLoader;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserCosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.levitycosmetics.utilities.uncategorizied.GradientUtils;
import com.reflexian.rapi.api.annotation.CommandInfo;
import com.reflexian.rapi.api.command.Command;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;

@CommandInfo(name = "search")
public class SearchCommand extends Command {
    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        } else if (!sender.hasPermission("levitycosmetics.search")) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }
        final UserData data = UserDataService.shared.retrieveUserFromCache(((Player) sender).getUniqueId());
        Set<UserCosmetic> cosmeticList = data.getUserCosmetics();
        if (args.length == 0) {
            sender.sendMessage("Please specify a cosmetic to search for.");
            return true;
        }

        String search = String.join(" ", args);
        cosmeticList = cosmeticList.stream().filter(cosmetic -> (GradientUtils.stripColor(cosmetic.getCosmetic().getName().toLowerCase())).contains(search.toLowerCase())).collect(Collectors.toSet());

        Set<UserCosmetic> finalCosmeticList = cosmeticList;
        InventoryProvider provider = new InventoryProvider() {
            @Override
            public void init(Player player, InventoryContents contents) {
                contents.fillBorders(ClickableItem.empty(ConfigurationLoader.GUI_CONFIG.getBackpackFillerItem()));
                for (UserCosmetic cosmetic : finalCosmeticList) {
                    contents.add(ClickableItem.of(cosmetic.getCosmetic().getItemStack(),e->{
                        data.equip(cosmetic);
                        player.closeInventory();
                    }));
                }
            }

            @Override
            public void update(Player player, InventoryContents contents) {

            }
        };

        SmartInventory inv = SmartInventory.builder().id("search").provider(provider).size(3, 9).title("Search (" + finalCosmeticList.size()+")").manager(LevityCosmetics.getInstance().getInventoryManager()).build();
        inv.open((Player) sender);
        return true;
    }
}
