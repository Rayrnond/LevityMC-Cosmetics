package com.reflexian.levitycosmetics.data.inventories;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.objects.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.utilities.GradientUtils;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;

public class BackpackInventory implements InventoryProvider {
    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("backpackInventory")
            .provider(new BackpackInventory())
            .size(5, 9)
            .title(GradientUtils.colorize(LevityCosmetics.getInstance().getGuiConfig().getBackpackTitle()))
            .manager(LevityCosmetics.getInstance().getInventoryManager())
            .build();


    @Override
    public void init(Player player, InventoryContents contents) {

        contents.fill(ClickableItem.empty(LevityCosmetics.getInstance().getGuiConfig().getGUIFiller()));
        contents.set(4,1, ClickableItem.of(LevityCosmetics.getInstance().getGuiConfig().getGUIBack(),e->{
            player.sendMessage("This is a placeholder message");
        }));
        contents.set(4,7, ClickableItem.of(LevityCosmetics.getInstance().getGuiConfig().getGUINext(),e->{
            player.sendMessage("This is a placeholder message");
        }));


        for (LChatColor chatColor : LevityCosmetics.getInstance().getChatColorConfig().getChatColors()) {
            contents.add(ClickableItem.of(chatColor.getItemStack(),e->{
                player.sendMessage(GradientUtils.colorize(chatColor.getName().formatted("This is a placeholder message")));
            }));
        }
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
