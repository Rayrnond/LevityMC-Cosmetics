package com.reflexian.levitycosmetics.data.objects.cosmetics.hat;

import com.reflexian.levitycosmetics.data.objects.cosmetics.CosmeticType;
import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LTabColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserCosmetic;
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
    private int chatcolorChance ;
    private int tabcolorChance ;
    private int glowChance;
    private int crownChance;
    private ItemStack helmet;
    private ItemStack itemStack;


    @Override
    public CosmeticType getType() {
        return CosmeticType.HAT;
    }

}
