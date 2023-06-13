package com.reflexian.levitycosmetics.data.objects.chatcolors;

import lombok.*;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Builder@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class LChatColor implements Serializable {

    private String id;
    private String name;
    private String color;
    private String permission;
    private ItemStack itemStack;




}
