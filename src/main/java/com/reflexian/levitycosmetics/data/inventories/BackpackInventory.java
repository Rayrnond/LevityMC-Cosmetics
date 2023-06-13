package com.reflexian.levitycosmetics.data.inventories;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.configs.GUIConfig;
import com.reflexian.levitycosmetics.data.objects.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.cosmetic.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.levitycosmetics.utilities.GradientUtils;
import com.reflexian.levitycosmetics.utilities.InvUtils;
import com.reflexian.levitycosmetics.utilities.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BackpackInventory implements InventoryProvider {
    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("backpackInventory")
            .provider(new BackpackInventory())
            .size(5, 9)
            .title(GradientUtils.colorize(LevityCosmetics.getInstance().getGuiConfig().getBackpackTitle()))
            .manager(LevityCosmetics.getInstance().getInventoryManager())
            .build();


    private int filtered = 0;

    @Override
    public void init(Player player, InventoryContents contents) {
        showInventory(player, contents, 0);
    }

    private void showInventory(Player player, InventoryContents contents, int page) {
        contents.fill(ClickableItem.empty(new ItemStack(Material.AIR)));
        GUIConfig guiConfig = LevityCosmetics.getInstance().getGuiConfig();
        contents.fillBorders(ClickableItem.empty(guiConfig.getBackpackFillerItem()));
        contents.set(InvUtils.getPos(guiConfig.getBackpackBackItemSlot()), ClickableItem.of(guiConfig.getBackpackBackItem(), e->{
            player.sendMessage("This is a placeholder message");
        }));
        contents.set(InvUtils.getPos(guiConfig.getBackpackNextItemSlot()), ClickableItem.of(guiConfig.getBackpackNextItem(), e->{
            player.sendMessage("This is a placeholder message");
        }));

        contents.set(InvUtils.getPos(guiConfig.getBackpackFilterSlot()), ClickableItem.of(getFilter(), e->{
            filtered++;
            if (filtered>4) filtered=0;
            showInventory(player, contents, page);
        }));


        var chatColors = Cosmetic.getAllCosmetics();  //UserDataService.shared.retrieveUserFromCache(player.getUniqueId()).getAllCosmetics();

        //todo some shit logic to make this work with pagination

        contents.add(ClickableItem.empty(new ItemStack(Material.DIAMOND_AXE)));
        for (var chatColor : chatColors) {
            LChatColor lChatColor = chatColor.asChatColor();
            Bukkit.broadcastMessage("FOUND ITEM! " + lChatColor.getItemStack().getType().name());
            contents.add(ClickableItem.of(lChatColor.getItemStack(),e->{
                player.sendMessage(GradientUtils.colorize(lChatColor.getName().formatted("This is a placeholder message")));
            }));
        }

        if (chatColors.size()==0) {
            contents.set(InvUtils.getPos(guiConfig.getBackpackErrorSlot()), ClickableItem.empty(guiConfig.getBackpackErrorItem()));
        }
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private ItemStack getFilter() {
        final GUIConfig guiConfig = LevityCosmetics.getInstance().getGuiConfig();
        ItemBuilder filterItem = new ItemBuilder(guiConfig.getBackpackFilterItem().clone());
        filterItem.clearLore();
        
        String selected = GradientUtils.colorize(guiConfig.getBackpackFilterSelectedIcon());
        String notSelected = GradientUtils.colorize(guiConfig.getBackpackFilterNotSelectedIcon());
        
        for (String l : guiConfig.getBackpackFilterItem().getLore()) {
            l=l.replace("%selection1%", filtered==0 ? selected : notSelected);
            l=l.replace("%selection2%", filtered==1 ? selected : notSelected);
            l=l.replace("%selection3%", filtered==2 ? selected : notSelected);
            l=l.replace("%selection4%", filtered==3 ? selected : notSelected);
            l=l.replace("%selection5%", filtered==4 ? selected : notSelected);
            l=GradientUtils.colorize(l);
            filterItem.lore(l);
        }
        return filterItem.build();
    }
}
