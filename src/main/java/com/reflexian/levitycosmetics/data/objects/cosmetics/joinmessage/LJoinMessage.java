package com.reflexian.levitycosmetics.data.objects.cosmetics.joinmessage;

import com.reflexian.levitycosmetics.data.objects.cosmetics.CosmeticType;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import lombok.*;
import org.bukkit.inventory.ItemStack;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LJoinMessage extends Cosmetic {

    private String name;
    private String message;
    private ItemStack itemStack;

    @Override
    public CosmeticType getType() {
        return CosmeticType.JOIN_MESSAGE;
    }

}
