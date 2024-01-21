package com.reflexian.levitycosmetics.data.inventories;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.Database;
import com.reflexian.levitycosmetics.data.configs.ConfigurationLoader;
import com.reflexian.levitycosmetics.data.configs.GUIConfig;
import com.reflexian.levitycosmetics.data.objects.cosmetics.CosmeticType;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.cosmetics.nickname.AssignedNickname;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.AssignedTitle;
import com.reflexian.levitycosmetics.data.objects.user.UserCosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.levitycosmetics.listeners.CosmeticListener;
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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.reflexian.levitycosmetics.listeners.CosmeticListener.lastChat;

public class BackpackInventory implements InventoryProvider {
    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("backpackInventory")
            .provider(new BackpackInventory())
            .size(5, 9)
            .title(GradientUtils.colorize(ConfigurationLoader.GUI_CONFIG.getBackpackTitle()))
            .manager(LevityCosmetics.getInstance().getInventoryManager())
            .build();




    @Override
    public void init(Player player, InventoryContents contents) {
        showInventory(player, contents, 0, 0);
    }

    private void showInventory(Player player, InventoryContents contents, int page, final int filtered) {

        final UserData userData = UserDataService.shared.retrieveUserFromCache(player.getUniqueId());
        Set<UserCosmetic> cosmetics = UserDataService.shared.retrieveUserFromCache(player.getUniqueId()).getUserCosmetics();
        cosmetics.removeIf(e-> e.getCosmetic() == null || e.getCosmeticType() == CosmeticType.TITLE || e.getCosmeticType() == CosmeticType.HAT); // removing titles because they should be assignedtitles

        contents.fill(ClickableItem.empty(new ItemStack(Material.AIR)));
        GUIConfig guiConfig = ConfigurationLoader.GUI_CONFIG;
        contents.fillBorders(ClickableItem.empty(guiConfig.getBackpackFillerItem()));

        contents.set(InvUtils.getPos(guiConfig.getBackpackCloseButtonSlot()), ClickableItem.of(guiConfig.getBackpackCloseButtonItem(), e->{
            player.closeInventory();
        }));

        contents.set(InvUtils.getPos(guiConfig.getBackpackFilterSlot()), ClickableItem.of(getFilter(filtered), e->{
            showInventory(player, contents, 0, filtered > 4 ? 0 : filtered+1);
        }));

        contents.set(InvUtils.getPos(guiConfig.getBackpackResetSlot()), ClickableItem.of(guiConfig.getBackpackResetItem(), e->{
            player.sendMessage(GradientUtils.colorize(LevityCosmetics.getInstance().getMessagesConfig().getBackpackResetMessage()));
            userData.setSelectedCrown(null);
            userData.setSelectedGlow(null);
            userData.setSelectedHat(null);
            userData.setSelectedTitle(null);
            userData.setSelectedNickname(null);
            userData.setSelectedChatColor(null);
            userData.setSelectedTabColor(null);
        }));


        switch (filtered) {
//            case 1 -> cosmetics = cosmetics.stream().filter(cosmetic -> cosmetic.getCosmeticType() == CosmeticType.ASSIGNED_HAT).collect(Collectors.toSet());
            case 1 -> cosmetics = cosmetics.stream().filter(cosmetic -> cosmetic.getCosmeticType() == CosmeticType.CHAT_COLOR).collect(Collectors.toSet());
            case 2 -> cosmetics = cosmetics.stream().filter(cosmetic -> cosmetic.getCosmeticType() == CosmeticType.ASSIGNED_NICKNAME || cosmetic.getCosmeticType().equals(CosmeticType.NICKNAME_PAINT)).collect(Collectors.toSet());
            case 3 -> cosmetics = cosmetics.stream().filter(cosmetic -> cosmetic.getCosmeticType() == CosmeticType.ASSIGNED_TITLE || cosmetic.getCosmeticType().equals(CosmeticType.TITLE_PAINT) || cosmetic.getCosmeticType().equals(CosmeticType.TITLE)).collect(Collectors.toSet());
            case 4 -> cosmetics = cosmetics.stream().filter(cosmetic -> cosmetic.getCosmeticType() == CosmeticType.TAB_COLOR).collect(Collectors.toSet());
            case 5 -> cosmetics = cosmetics.stream().filter(cosmetic -> cosmetic.getCosmeticType() == CosmeticType.CROWN).collect(Collectors.toSet());
            default -> {}
        }

        cosmetics = cosmetics.stream()
                .sorted((o1, o2) -> o1.getCosmetic().getName().compareToIgnoreCase(o2.getCosmetic().getName()))
                .sorted((o1, o2) -> Integer.compare(o2.getCosmetic().getRarity(), o1.getCosmetic().getRarity()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        cosmetics = cosmetics.stream().skip(page * 21L).limit(21).collect(Collectors.toCollection(LinkedHashSet::new));


        int slot = 10;
        for (var cosmetic : cosmetics) {
            if (slot == 17 || slot == 18) slot = 19;
            else if (slot == 26 || slot == 27) slot = 28;

            ItemStack itemStack = cosmetic.getCosmetic().getItemStack().clone();
            if (cosmetic.isSelected()) {
                ItemBuilder.addLore(itemStack, " ", LevityCosmetics.getInstance().getMessagesConfig().getBackpackItemSelectedMessage());
            }

            contents.set(InvUtils.getPos(slot), ClickableItem.of(itemStack, e -> {

                if (cosmetic.getCosmeticType() == CosmeticType.ASSIGNED_NICKNAME) {
                    if (((AssignedNickname) cosmetic.getCosmetic()).getContent().isEmpty()) {
                        String message = GradientUtils.colorize(LevityCosmetics.getInstance().getMessagesConfig().getNicknameTitleMessage());

                        player.closeInventory();
                        player.sendTitle(" ", message, 0, 100, 0);
                        player.sendMessage("\n \n \n"+message);
                        lastChat.put(player.getUniqueId(), System.currentTimeMillis()+30000);
                        CosmeticListener.chatToNickMap.put(player.getUniqueId(), (AssignedNickname) cosmetic.getCosmetic());
                        return;
                    }
                }

                if (cosmetic.getCosmeticType() == CosmeticType.TITLE_PAINT || cosmetic.getCosmeticType() == CosmeticType.NICKNAME_PAINT) {
                    showSelectMenu(player, cosmetic);
                    return;
                }
                if (cosmetic.isSelected()) {
                    userData.unequip(cosmetic.getCosmetic());
                    cosmetic.setSelected(false);
                } else {
                    userData.equip(cosmetic);
                    cosmetic.setSelected(true);
                }

            }));

            slot++;
            if (slot > 34) {
                break;
            }
        }

        int basePages = LevityCosmetics.getInstance().getDefaultConfig().getBackpackPages();

        if (cosmetics.size()==0) {
            contents.set(InvUtils.getPos(guiConfig.getBackpackErrorSlot()), ClickableItem.empty(guiConfig.getBackpackErrorItem()));
        } else {
            if (page < basePages + userData.getExtraPages()) {
                contents.set(InvUtils.getPos(guiConfig.getBackpackNextItemSlot()), ClickableItem.of(guiConfig.getBackpackNextItem(), e -> {
                    showInventory(player, contents, page + 1, filtered);
                }));
            } else {
                contents.set(InvUtils.getPos(guiConfig.getBackpackNextItemSlot()), ClickableItem.of(guiConfig.getBackpackPurchasePageButton(), e -> {
                    if (userData.getCredits() < LevityCosmetics.getInstance().getDefaultConfig().getBackpackPrice()) {
                        player.sendMessage("§cYou don't have enough credits to purchase this page!");
                        return;
                    }
                    userData.removeCredits(LevityCosmetics.getInstance().getDefaultConfig().getBackpackPrice());
                    userData.setExtraPages(userData.getExtraPages() + 1);
                    player.sendMessage("§aYou have purchased an extra page for your backpack!");
                    showInventory(player, contents, page + 1, filtered);
                    UserDataService.shared.save(userData,ef->{});
                }));
            }
        }
        if (page!=0) {
            contents.set(InvUtils.getPos(guiConfig.getBackpackBackItemSlot()), ClickableItem.of(guiConfig.getBackpackBackItem(), e->{
                showInventory(player, contents, page-1, filtered);
            }));
        }


    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private ItemStack getFilter(int filtered) {
        final GUIConfig guiConfig = ConfigurationLoader.GUI_CONFIG;
        ItemBuilder filterItem = new ItemBuilder(guiConfig.getBackpackFilterItem().clone());
        if (filterItem.getMeta()!=null && filterItem.getMeta().getLore() != null) {
            filterItem.clearLore();

            String selected = guiConfig.getBackpackFilterSelectedIcon();
            String notSelected = guiConfig.getBackpackFilterNotSelectedIcon();

            for (String l : guiConfig.getBackpackFilterItem().clone().getLore()) {
                l=l.replace("%selection1%", filtered==0 ? selected : notSelected);
                l=l.replace("%selection2%", filtered==1 ? selected : notSelected);
                l=l.replace("%selection3%", filtered==2 ? selected : notSelected);
                l=l.replace("%selection4%", filtered==3 ? selected : notSelected);
                l=l.replace("%selection5%", filtered==4 ? selected : notSelected);
                l=l.replace("%selection6%", filtered==5 ? selected : notSelected);
                filterItem.lore(l);
            }
        }
        return filterItem.build();
    }

    private void showSelectMenu(Player player, UserCosmetic cosmetic) {
        UserData data = UserDataService.shared.retrieveUserFromCache(player.getUniqueId());

        final Set<Cosmetic> cosmeticList;
        final boolean titlepaints = cosmetic.getCosmeticType() == CosmeticType.TITLE_PAINT;
        if (titlepaints) {
            cosmeticList = new HashSet<>(new HashSet<>(data.getAssignedTitles()));
        } else {
            cosmeticList = new HashSet<>(new HashSet<>(data.getAssignedNicknames()));
            cosmeticList.removeIf(c->((AssignedNickname)c).getContent().isEmpty());
        }

        InventoryProvider provider = new InventoryProvider() {
            @Override
            public void init(Player player, InventoryContents contents) {
                try {
                    contents.fillBorders(ClickableItem.empty(ConfigurationLoader.GUI_CONFIG.getBackpackFillerItem()));
                    for (Cosmetic c : cosmeticList) {
                        contents.add(ClickableItem.of(c.getItemStack(),e->{

                            try {
                                if (titlepaints) {
                                    AssignedTitle title = (AssignedTitle) c;
                                    title.setPaint(cosmetic.getCosmetic().asTitlePaint());
                                    Database.shared.save(title);
                                    player.sendMessage("§aYou have set the title paint §e" + cosmetic.getCosmetic().getName() + "§a for the title §e" + title.getTitle().getName() + "§a.");
                                } else {
                                    AssignedNickname nickname = (AssignedNickname) c;
                                    nickname.setPaint(cosmetic.getCosmetic().asNicknamePaint());
                                    Database.shared.save(nickname);
                                    player.sendMessage("§aYou have set the nickname paint §e" + cosmetic.getCosmetic().getName() + "§a for the nickname §e" + nickname.getContent() + "§a.");
                                }

                                data.getUserCosmetics().remove(cosmetic);
                                UserDataService.shared.save(data,f->{});
                                player.closeInventory();
                            }catch (Exception ef) {
                                ef.printStackTrace();
                                player.closeInventory();
                                player.sendMessage("§cSomething went wrong, report this! *error bpselectcos2*");
                            }
                        }));
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    player.closeInventory();
                    player.sendMessage("§cSomething went wrong, report this! *error bpselectcos*");
                }
            }

            @Override
            public void update(Player player, InventoryContents contents) {

            }
        };

        SmartInventory inv = SmartInventory.builder().id("consume").provider(provider).size(5, 9).title("Select a cosmetic").manager(LevityCosmetics.getInstance().getInventoryManager()).build();
        inv.open(player);
    }
}
