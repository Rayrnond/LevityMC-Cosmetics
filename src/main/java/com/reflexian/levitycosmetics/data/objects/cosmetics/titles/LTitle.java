package com.reflexian.levitycosmetics.data.objects.cosmetics.titles;

import com.reflexian.levitycosmetics.data.objects.cosmetics.CosmeticType;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import lombok.*;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Builder@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class LTitle extends Cosmetic implements Serializable {

    private String name;
    private String tag;
    private ItemStack itemStack;

    @Override
    public CosmeticType getType() {
        return CosmeticType.TITLE;
    }
}
