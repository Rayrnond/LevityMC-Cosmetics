package com.reflexian.levitycosmetics.data.objects.hat;

import com.reflexian.levitycosmetics.data.objects.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.cosmetic.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.titles.LTitle;
import lombok.*;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Builder@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class LHat extends Cosmetic implements Serializable {

    // a collection of helmet, crown, chat color, and other cosmetic objects

    private String id;
    private String name;
    private String chatColorId=""; // empty = none
    private String lTitleId="";
    private ItemStack itemStack;

    @Override
    public String getUniqueId() {
        return "lhat_"+getId();
    }
}
