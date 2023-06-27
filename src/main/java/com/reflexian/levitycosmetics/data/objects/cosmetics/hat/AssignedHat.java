package com.reflexian.levitycosmetics.data.objects.cosmetics.hat;

import com.reflexian.levitycosmetics.data.objects.cosmetics.CosmeticType;
import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LTabColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.LTitle;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.LTitlePaint;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor@NoArgsConstructor@Getter
public class AssignedHat extends Cosmetic {

    private UUID uuid;
    private String cosmeticId = "";
    @Setter private LHat lHat=null;
    @Setter private transient LChatColor chatColor = null; // empty = none
    @Setter private transient LTabColor tabColor = null; // empty = none
    @Setter private transient LGlow glow = null; // empty = none
    @Setter private transient LCrown crown = null; // empty = none

    public AssignedHat(UUID uuid, String cosmeticId) {
        this.uuid = uuid;
        this.cosmeticId = cosmeticId;
    }


    public static AssignedHat fromResultSet(ResultSet resultSet) throws SQLException {
        final UUID uuid = UUID.fromString(resultSet.getString("user_id"));
        final String id = resultSet.getString("cosmeticId");
        final Cosmetic hat = Cosmetic.getCosmetic(resultSet.getString("hatId"));
        final Cosmetic chatColor = Cosmetic.getCosmetic(resultSet.getString("chatcolorId"));
        final Cosmetic tabColor = Cosmetic.getCosmetic(resultSet.getString("tabcolorId"));
        final Cosmetic glow = Cosmetic.getCosmetic(resultSet.getString("glowId"));
        final Cosmetic crown = Cosmetic.getCosmetic(resultSet.getString("crownId"));
        return new AssignedHat(uuid, id, hat == null ? null : hat.asLHat(), chatColor == null ? null : chatColor.asChatColor(), tabColor == null ? null : tabColor.asTabColor(), glow == null ? null : glow.asGlow(), crown == null ? null : crown.asCrown());
    }

    public String toSQL() {
        // dont need sql protection due to the uuid being a valid uuid, title is validated, paint is validated.
        return "INSERT INTO `hats` (`user_id`, `cosmeticId`, `hatId`, `chatcolorId`, `tabcolorId`, `glowId`, `crownId`) VALUES ('" + uuid.toString() + "', '" + cosmeticId + "', '"  + (lHat == null ? "" : lHat.getName()) + "', '" + (chatColor == null ? "" : chatColor.getName()) + "', '" + (tabColor == null ? "" : tabColor.getName()) + "', '" + (glow == null ? "" : glow.getName()) + "', '" + (crown == null ? "" : crown.getName()) + "') ON DUPLICATE KEY UPDATE `hatId` = '" + (lHat == null ? "" : lHat.getName()) + "', `chatcolorId` = '" + (chatColor == null ? "" : chatColor.getName()) + "', `tabcolorId` = '" + (tabColor == null ? "" : tabColor.getName()) + "', `glowId` = '" + (glow == null ? "" : glow.getName()) + "', `crownId` = '" + (crown == null ? "" : crown.getName()) + "';";
    }

    public void unequipHat(UserData userData) {

        userData.getUserCosmetics().stream().filter(cosmetic -> (cosmetic.getCosmeticType() == CosmeticType.ASSIGNED_HAT)).forEach(cosmetic -> {
            cosmetic.setSelected(false);
        });

        Bukkit.getPlayer(userData.getUuid()).getInventory().setHelmet(null);
        if (chatColor != null) {
            userData.unequip(chatColor);
        }
        if (tabColor != null) {
            userData.unequip(tabColor);
        }
        if (glow != null) {
            userData.unequip(glow);
        }
        if (crown != null) {
            userData.unequip(crown);
        }
        userData.setSelectedHat(null);
    }

    public void equipHat(Player player) {
        UserData userData = UserDataService.shared.retrieveUserFromCache(player.getUniqueId());
        userData.setSelectedHat(this);

        NBTItem nbtItem = new NBTItem(lHat.getHelmet().clone());
        nbtItem.setBoolean("levitycosmeticshelmet", true);
        Bukkit.getPlayer(userData.getUuid()).getInventory().setHelmet(nbtItem.getItem());


        if (chatColor != null) {
            userData.equip(chatColor);
        }
        if (tabColor != null) {
            userData.equip(tabColor);
        }
        if (glow != null) {
            userData.equip(glow);
        }
        if (crown != null) {
            userData.equip(crown);
        }

    }
    

    @Override
    public ItemStack getItemStack() {

        ItemStack itemStack = lHat.getItemStack().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        // replace lore %glow% %crown% %chatcolor% %tabcolor%

        List<String> lore = itemMeta.getLore();
        lore.replaceAll(s1 -> s1.replace("%glow%", glow == null ? "None" : glow.getName()).replace("%crown%", crown == null ? "None" : crown.getName()).replace("%chatcolor%", chatColor == null ? "None" : chatColor.getName()).replace("%tabcolor%", tabColor == null ? "None" : tabColor.getName()));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public String getName() {
        return cosmeticId;
    }

    @Override
    public CosmeticType getType() {
        return CosmeticType.ASSIGNED_HAT;
    }
}
