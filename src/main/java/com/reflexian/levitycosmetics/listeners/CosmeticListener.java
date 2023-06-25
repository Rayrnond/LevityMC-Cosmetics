package com.reflexian.levitycosmetics.listeners;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.Database;
import com.reflexian.levitycosmetics.data.objects.cosmetics.nickname.AssignedNickname;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import com.reflexian.levitycosmetics.utilities.uncategorizied.GradientUtils;
import com.reflexian.rapi.api.annotation.Registrar;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Registrar
public class CosmeticListener implements Listener {

    private final Map<UUID, Long> lastChat = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        UserData userData = UserDataService.shared.retrieveUserFromCache(event.getPlayer().getUniqueId());

        if (lastChat.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            if (System.currentTimeMillis() > lastChat.get(event.getPlayer().getUniqueId())) {
                lastChat.remove(event.getPlayer().getUniqueId());
                event.getPlayer().sendMessage("§c§lYou ran out of time to type your nickname.");
                return;
            } else if (event.getMessage().equalsIgnoreCase("cancel")) {
                lastChat.remove(event.getPlayer().getUniqueId());
                event.getPlayer().sendMessage("§c§lYou have cancelled your nickname change.");
                return;
            }

            if (!event.getMessage().matches("^[a-zA-Z0-9_]{3,16}$")) {
                event.getPlayer().sendMessage(GradientUtils.colorize(LevityCosmetics.getInstance().getMessagesConfig().getNicknameErrorMessage()));
                return;
            } else if (event.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
                event.getPlayer().sendMessage("§c§lYou must be holding a nickname ticket to use this.");
                return;
            }
            NBTItem nbtItem = new NBTItem(event.getPlayer().getInventory().getItemInMainHand());
            if (!nbtItem.hasKey("nicknameticket") || !nbtItem.getBoolean("nicknameticket")) {
                event.getPlayer().sendMessage("§c§lYou must be holding a nickname ticket to use this.");
                return;
            }

            event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount()-1);

            event.getPlayer().sendMessage(GradientUtils.colorize(LevityCosmetics.getInstance().getMessagesConfig().getNicknameChangedMessage().replace("%nick%", event.getMessage())));
            AssignedNickname assignedNickname = new AssignedNickname(event.getPlayer().getUniqueId(), new Random().nextInt(3293)+event.getPlayer().getUniqueId().toString().substring(0,10).replace("-",""), event.getMessage(),null);
            userData.getAssignedNicknames().add(assignedNickname);
            userData.setSelectedNickname(assignedNickname);
            Database.shared.save(assignedNickname);
            lastChat.remove(event.getPlayer().getUniqueId());
            return;
        }

        if (userData.getSelectedChatColor() != null) {
            event.setMessage(GradientUtils.colorize(userData.getSelectedChatColor().getColor().replace("%message%", event.getMessage())));
        }
    }

    @EventHandler
    public void onRightClickNicknameTicket(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        NBTItem nbtItem = new NBTItem(event.getItem());
        if (nbtItem.hasKey("nicknameticket") && nbtItem.getBoolean("nicknameticket")) {
            // start

            String message = GradientUtils.colorize(LevityCosmetics.getInstance().getMessagesConfig().getNicknameTitleMessage());

            event.getPlayer().sendTitle(" ", message, 0, 100, 0);
            event.getPlayer().sendMessage("\n \n \n"+message);
            lastChat.put(event.getPlayer().getUniqueId(), System.currentTimeMillis()+30000);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        lastChat.remove(event.getPlayer().getUniqueId());
    }

}
