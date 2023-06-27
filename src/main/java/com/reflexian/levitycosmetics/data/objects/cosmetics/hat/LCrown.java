package com.reflexian.levitycosmetics.data.objects.cosmetics.hat;

import com.reflexian.levitycosmetics.data.objects.cosmetics.CosmeticType;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import lombok.*;
import org.bukkit.inventory.ItemStack;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LCrown extends Cosmetic {

    private String name;
    private String symbol;
    private ItemStack itemStack;

    @Override
    public CosmeticType getType() {
        return CosmeticType.CROWN;
    }

}
