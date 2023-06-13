package com.reflexian.levitycosmetics.data.inventories;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.objects.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.utilities.GradientUtils;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatColorInventory implements InventoryProvider {
    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("chatColorInventory")
            .provider(new ChatColorInventory())
            .size(3, 9)
            .title("Chat Colors")
            .manager(LevityCosmetics.getInstance().getInventoryManager())
            .build();


    @Override
    public void init(Player player, InventoryContents contents) {
        int i = 0;
        for (LChatColor chatColor : LevityCosmetics.getInstance().getChatColorConfig().getChatColors()) {
            contents.set(1, i, ClickableItem.of(chatColor.getItemStack(),e->{
                player.sendMessage(GradientUtils.colorize(chatColor.getName().formatted("This is a placeholder message")));
            }));
            i++;
        }
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
