package com.reflexian.levitycosmetics.data.objects.cosmetics.hat;

import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import lombok.*;
import org.bukkit.inventory.ItemStack;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LGlow extends Cosmetic {

    private String name;
    private String color; //BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, PURPLE, YELLOW, WHITE, NONE;
    private ItemStack itemStack;

}
