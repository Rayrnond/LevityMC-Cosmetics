package com.reflexian.levitycosmetics.data.objects.titles;

import com.reflexian.levitycosmetics.data.objects.cosmetic.Cosmetic;
import lombok.*;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Builder@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class LTitle extends Cosmetic implements Serializable {

    private String id;
    private String name;
    private String tag;
    private ItemStack itemStack;

    @Override
    public String getUniqueId() {
        return "ltitle_"+getId();
    }
}
