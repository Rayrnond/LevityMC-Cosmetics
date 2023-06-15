package com.reflexian.levitycosmetics.data.inventories;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.configs.GUIConfig;
import com.reflexian.levitycosmetics.data.objects.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.cosmetic.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.titles.LTitle;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.levitycosmetics.utilities.uncategorizied.GradientUtils;
import com.reflexian.levitycosmetics.utilities.uncategorizied.InvUtils;
import com.reflexian.levitycosmetics.utilities.uncategorizied.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.stream.Collectors;

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
        showInventory(player, contents, 0, 0);
    }

    private void showInventory(Player player, InventoryContents contents, int page, final int filtered) {
        contents.fill(ClickableItem.empty(new ItemStack(Material.AIR)));
        GUIConfig guiConfig = LevityCosmetics.getInstance().getGuiConfig();
        contents.fillBorders(ClickableItem.empty(guiConfig.getBackpackFillerItem()));
        if (page!=0) {
            contents.set(InvUtils.getPos(guiConfig.getBackpackBackItemSlot()), ClickableItem.of(guiConfig.getBackpackBackItem(), e->{
                showInventory(player, contents, page-1, filtered);
            }));
        }
        contents.set(InvUtils.getPos(guiConfig.getBackpackNextItemSlot()), ClickableItem.of(guiConfig.getBackpackNextItem(), e->{
            showInventory(player, contents, page+1, filtered);
        }));

        contents.set(InvUtils.getPos(guiConfig.getBackpackFilterSlot()), ClickableItem.of(getFilter(filtered), e->{
            showInventory(player, contents, 0, filtered > 4 ? 0 : filtered+1);
        }));


        var cosmetics = Cosmetic.getAllCosmetics(); //UserDataService.shared.retrieveUserFromCache(player.getUniqueId()).getAllCosmetics();

        switch (filtered) {
            case 1 -> cosmetics = cosmetics.stream().filter(cosmetic -> cosmetic instanceof LChatColor).collect(Collectors.toSet());
            case 4 -> cosmetics = cosmetics.stream().filter(cosmetic -> cosmetic instanceof LTitle).collect(Collectors.toSet());
            default -> {}
//            case 2 -> cosmetics = cosmetics.stream().filter(cosmetic -> cosmetic instanceof LParticle).collect(Collectors.toList());
//            case 3 -> cosmetics = cosmetics.stream().filter(cosmetic -> cosmetic instanceof LEmote).collect(Collectors.toList());
//            case 4 -> cosmetics = cosmetics.stream().filter(cosmetic -> cosmetic instanceof LTrail).collect(Collectors.toList());
        }


        // sort cosmetics by name
        cosmetics = cosmetics.stream().sorted((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName())).collect(Collectors.toCollection(LinkedHashSet::new));

        // get first 21 cosmetics depending on page
        cosmetics = cosmetics.stream().skip(page * 21L).limit(21).collect(Collectors.toCollection(LinkedHashSet::new));

        // start with slot 10, every 7 add 2 until 21
        int slot = 10;
        for (var cosmetic : cosmetics) {
            if (slot == 17 || slot == 18) slot = 19;
            else if (slot == 26 || slot == 27) slot = 28;

            contents.set(InvUtils.getPos(slot), ClickableItem.of(cosmetic.getItemStack(), e -> {
                UserData userData = UserDataService.shared.retrieveUserFromCache(player.getUniqueId());
                if (cosmetic instanceof LChatColor)
                    userData.setSelectedChatColor(cosmetic.asChatColor());
                else if (cosmetic instanceof LTitle)
                    userData.setSelectedTitle(cosmetic.asTitle());

                player.sendMessage(GradientUtils.colorize(LevityCosmetics.getInstance().getMessagesConfig().getBackpackSelectMessage().replace("%cosmetic%", GradientUtils.colorize(cosmetic.getName()))));
                player.closeInventory();

//                else if (cosmetic instanceof LParticle)
//                    userData.setSelectedParticle((LParticle) cosmetic);
//                else if (cosmetic instanceof LEmote)
//                    userData.setSelectedEmote((LEmote) cosmetic);
//                else if (cosmetic instanceof LTrail)
//                    userData.setSelectedTrail((LTrail) cosmetic);
//                else if (cosmetic instanceof LCosmetic)
//
            }));

            slot++;
            if (slot > 34) {
                break;
            }
        }

        if (cosmetics.size()==0 && page == 0) {
            contents.set(InvUtils.getPos(guiConfig.getBackpackErrorSlot()), ClickableItem.empty(guiConfig.getBackpackErrorItem()));
        }
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private ItemStack getFilter(int filtered) {
        final GUIConfig guiConfig = LevityCosmetics.getInstance().getGuiConfig();
        ItemBuilder filterItem = new ItemBuilder(guiConfig.getBackpackFilterItem().clone());
        if (filterItem.getMeta()!=null && filterItem.getMeta().getLore() != null) {
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
        }
        return filterItem.build();
    }
}
