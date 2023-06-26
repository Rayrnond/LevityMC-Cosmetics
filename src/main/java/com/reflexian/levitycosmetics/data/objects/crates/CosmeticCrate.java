package com.reflexian.levitycosmetics.data.objects.crates;

import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder@Getter@AllArgsConstructor
public class CosmeticCrate implements Serializable {

    public static final Map<String,CosmeticCrate> CRATES = new HashMap<>();

    private String name;
    private ItemStack itemstack;
    private List<String> potentialCosmetics;

    public boolean roll(Player player) {
        final UserData userData = UserDataService.shared.retrieveUserFromCache(player.getUniqueId());

        Collections.shuffle(potentialCosmetics);
        final Cosmetic cosmetic = potentialCosmetics.stream().map(Cosmetic::getCosmetic).filter(c -> !userData.getAllCosmetics().contains(c)).findFirst().orElse(null);
        if (cosmetic == null) {
            player.sendMessage("§cYou own all cosmetics this crate offers!");
            return false;
        }

        cosmetic.giveToUser(userData);

        player.getInventory().setItemInMainHand(player.getInventory().getItemInMainHand().asQuantity(player.getInventory().getItemInMainHand().getAmount()-1));
        player.sendMessage("§bYou have received the §f"+cosmetic.getName()+"§b cosmetic!");
        return true;
    }

    public ItemStack getItemStack() {
        NBTItem nbtItem = new NBTItem(itemstack);
        nbtItem.setString("cosmeticCrate", name);
        nbtItem.setBoolean("crate", true);
        return nbtItem.getItem();
    }




}
