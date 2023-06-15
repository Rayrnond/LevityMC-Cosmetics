package com.reflexian.levitycosmetics.data.objects.chatcolors;

import com.reflexian.levitycosmetics.data.objects.cosmetic.Cosmetic;
import lombok.*;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor@NoArgsConstructor
public class LChatColor extends Cosmetic implements Serializable {

    private String id;
    private String name;
    private String color;
    private ItemStack itemStack;

    @Override
    public String getUniqueId() {
        return "lchatcolor_"+getId();
    }
}
