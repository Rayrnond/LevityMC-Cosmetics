package com.reflexian.levitycosmetics.data.objects.cosmetics.nickname;

import com.reflexian.levitycosmetics.data.objects.cosmetics.CosmeticType;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import lombok.*;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Builder@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class LNicknamePaint extends Cosmetic implements Serializable {

    private String name;
    private String color;
    private ItemStack itemStack;


    @Override
    public CosmeticType getType() {
        return CosmeticType.NICKNAME_PAINT;
    }
}
