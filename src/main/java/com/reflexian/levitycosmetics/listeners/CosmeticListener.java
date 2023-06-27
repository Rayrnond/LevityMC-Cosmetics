package com.reflexian.levitycosmetics.listeners;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.Database;
import com.reflexian.levitycosmetics.data.inventories.BackpackInventory;
import com.reflexian.levitycosmetics.data.objects.cosmetics.nickname.AssignedNickname;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.levitycosmetics.utilities.uncategorizied.GradientUtils;
import com.reflexian.rapi.api.annotation.Registrar;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Registrar
public class CosmeticListener implements Listener {

    public static final Map<UUID, Long> lastChat = new HashMap<>();
    public static final Map<UUID, AssignedNickname> chatToNickMap = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        UserData userData = UserDataService.shared.retrieveUserFromCache(event.getPlayer().getUniqueId());

        if (lastChat.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            if (System.currentTimeMillis() > lastChat.get(event.getPlayer().getUniqueId()) || !chatToNickMap.containsKey(event.getPlayer().getUniqueId())) {
                lastChat.remove(event.getPlayer().getUniqueId());
                event.getPlayer().sendMessage("§c§lYou ran out of time to type your nickname.");
                return;
            } else if (event.getMessage().equalsIgnoreCase("cancel")) {
                lastChat.remove(event.getPlayer().getUniqueId());
                chatToNickMap.remove(event.getPlayer().getUniqueId());
                event.getPlayer().sendMessage("§c§lYou have cancelled your nickname change.");
                return;
            }

            if (!event.getMessage().matches("^[a-zA-Z0-9_]{3,16}$")) {
                event.getPlayer().sendMessage(GradientUtils.colorize(LevityCosmetics.getInstance().getMessagesConfig().getNicknameErrorMessage()));
                return;
            }

            event.getPlayer().sendMessage(GradientUtils.colorize(LevityCosmetics.getInstance().getMessagesConfig().getNicknameChangedMessage().replace("%nick%", event.getMessage())));


            AssignedNickname assignedNickname = chatToNickMap.get(event.getPlayer().getUniqueId());
            assignedNickname.setContent(event.getMessage());
//            userData.equip(assignedNickname);
            Database.shared.save(assignedNickname);
            lastChat.remove(event.getPlayer().getUniqueId());
            chatToNickMap.remove(event.getPlayer().getUniqueId());
            Bukkit.getScheduler().runTask(LevityCosmetics.getInstance(), () -> BackpackInventory.INVENTORY.open(event.getPlayer()));
            return;
        }

        if (userData.getSelectedChatColor() != null) {
            event.setMessage(GradientUtils.colorize(userData.getSelectedChatColor().getColor().replace("%message%", event.getMessage())));
        }
    }

//    @EventHandler
//    public void onRightClickNicknameTicket(PlayerInteractEvent event) {
//        if (event.getItem() == null) return;
//        NBTItem nbtItem = new NBTItem(event.getItem());
//        if (nbtItem.hasKey("nicknameticket") && nbtItem.getBoolean("nicknameticket")) {
//            // start
//
//            String message = GradientUtils.colorize(LevityCosmetics.getInstance().getMessagesConfig().getNicknameTitleMessage());
//
//            event.getPlayer().sendTitle(" ", message, 0, 100, 0);
//            event.getPlayer().sendMessage("\n \n \n"+message);
//            lastChat.put(event.getPlayer().getUniqueId(), System.currentTimeMillis()+30000);
//        }
//    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        lastChat.remove(event.getPlayer().getUniqueId());
    }

}
