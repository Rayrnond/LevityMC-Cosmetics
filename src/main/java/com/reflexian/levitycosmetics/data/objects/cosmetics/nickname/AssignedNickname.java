package com.reflexian.levitycosmetics.data.objects.cosmetics.nickname;

import com.reflexian.levitycosmetics.data.configs.ConfigurationLoader;
import com.reflexian.levitycosmetics.data.objects.cosmetics.CosmeticType;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.utilities.uncategorizied.GradientUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@AllArgsConstructor@NoArgsConstructor@Getter
public class AssignedNickname extends Cosmetic{

    private UUID uuid;
    private String cosmeticId = "";
    @Setter private String content;
    @Nullable
    private LNicknamePaint paint=null;
    private transient String paintId = ""; // failover if paint is null

    public void setPaint(@Nullable LNicknamePaint paint) {
        this.paint = paint;
        if (paint != null) {
            this.paintId = paint.getName();
        }
    }

    public AssignedNickname(UUID uuid, String cosmeticId, String content, @Nullable LNicknamePaint paint) {
        
        this.uuid = uuid;
        this.cosmeticId = cosmeticId;
        this.content = content;
        if (paint != null) {
            this.paint = paint;
            this.paintId = paint.getName();
        }
    }

    public static AssignedNickname fromResultSet(ResultSet resultSet) throws SQLException {

        if (resultSet.getString("cosmeticId") == null) throw new RuntimeException("Failed to load nickname cosmetic: " + resultSet.getString("cosmeticId")+ ". No cosmetic Id");
        else if (resultSet.getString("content") == null) throw new RuntimeException("Failed to load nickname cosmetic: " + resultSet.getString("cosmeticId")+ ". No content " + resultSet.getString("content"));
        else if (resultSet.getString("paintId") == null) throw new RuntimeException("Failed to load nickname cosmetic: " + resultSet.getString("cosmeticId")+ ". No paint Id " + resultSet.getString("paintId"));

        final Cosmetic paint = Cosmetic.getCosmetic(resultSet.getString("paintId"));
//        if (paint == null) throw new RuntimeException("Failed to load nickname cosmetic: " + resultSet.getString("cosmeticId")+ ". PiD:"+resultSet.getString("paintId"));
        return new AssignedNickname(UUID.fromString(resultSet.getString("user_id")), resultSet.getString("cosmeticId"), resultSet.getString("content"), paint == null ? null : paint.asNicknamePaint());
    }

    public String toSQL() {
        // insert, update if exists
        return "INSERT INTO `nicknames` (`user_id`, `cosmeticId`, `content`, `paintId`) VALUES ('" + uuid.toString() + "', '" + cosmeticId + "', '"  + content + "', '" + paintId + "') ON DUPLICATE KEY UPDATE `content` = '" + content + "', `paintId` = '" + paintId + "';";
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack itemstack = ConfigurationLoader.GUI_CONFIG.getNicknameCosmeticBackpackItem().clone();
        ItemMeta meta = itemstack.getItemMeta();

        String name = content;
        if (!content.isEmpty() && paint != null) {
            name = paint.getColor().replace("%player%", content);
            name = GradientUtils.colorize(name);
        }
        final String finalName = name;

        meta.setDisplayName(meta.getDisplayName().replace("%nickname%", (content.isEmpty() ? "Click to set a nickname" : name)));
        // get lore
        meta.setLore(meta.getLore().stream().map(e->e.replace("%nickname%", (content.isEmpty() ? "": finalName))).toList());
        itemstack.setItemMeta(meta);
        return itemstack;
    }

    @Override
    public String getName() {
        return cosmeticId;
    }

    @Override
    public CosmeticType getType() {
        return CosmeticType.ASSIGNED_NICKNAME;
    }
}
