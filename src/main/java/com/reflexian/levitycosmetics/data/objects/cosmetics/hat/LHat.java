package com.reflexian.levitycosmetics.data.objects.cosmetics.hat;

import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LTabColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Builder@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class LHat extends Cosmetic implements Serializable {

    // a collection of helmet, crown, chat color, and other cosmetic objects

    private String name;
    private transient String chatColor = ""; // empty = none
    private transient String tabColor = ""; // empty = none
    private transient String glow = ""; // empty = none
    private transient String crown = ""; // empty = none
    private ItemStack helmet;
    private ItemStack itemStack;

    public void unequipHat(UserData userData) {
        Bukkit.getPlayer(userData.getUuid()).getInventory().setHelmet(null);
        userData.setSelectedHat(null);
        userData.setSelectedCrown(null);
        userData.setSelectedGlow(null);
        userData.setSelectedTabColor(null);
        userData.setSelectedChatColor(null);
    }

    public void equipHat(Player player) {
        UserData userData = UserDataService.shared.retrieveUserFromCache(player.getUniqueId());
        userData.setSelectedHat(this);

        NBTItem nbtItem = new NBTItem(helmet.clone());
        nbtItem.setBoolean("levitycosmeticshelmet", true);
        Bukkit.getPlayer(userData.getUuid()).getInventory().setHelmet(nbtItem.getItem());


        if (chatColor != null && !chatColor.equals("")) {
            LChatColor lChatColor = Cosmetic.getCosmetic(chatColor).asChatColor();
            userData.equip(lChatColor);
        }
        if (tabColor != null && !tabColor.equals("")) {
            LTabColor lTabColor = Cosmetic.getCosmetic(tabColor).asTabColor();
            userData.equip(lTabColor);
        }
        if (glow != null && !glow.equals("")) {
            userData.equip(Cosmetic.getCosmetic(glow).asGlow());
        }
        if (crown != null && !crown.equals("")) {
            userData.equip(Cosmetic.getCosmetic(crown).asCrown());
        }

    }

}
