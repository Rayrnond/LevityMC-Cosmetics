package com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors;

import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import lombok.*;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Builder @Getter @Setter @AllArgsConstructor@NoArgsConstructor
public class LChatColor extends Cosmetic implements Serializable {

    private String name;
    private String color;
    private ItemStack itemStack;


}
