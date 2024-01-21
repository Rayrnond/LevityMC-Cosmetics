package com.reflexian.levitycosmetics.data.objects.cosmetics.titles;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.objects.cosmetics.CosmeticType;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.utilities.uncategorizied.GradientUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@AllArgsConstructor@NoArgsConstructor@Getter
public class AssignedTitle extends Cosmetic {

    private UUID uuid;
    private String cosmeticId = "";
    @Setter private LTitle title;
    @Setter private LTitlePaint paint;

    public AssignedTitle(UUID uuid, LTitle title, LTitlePaint paint) {
        this.uuid = uuid;
        this.title = title;
        this.paint = paint;
    }

    public static AssignedTitle fromResultSet(ResultSet resultSet) throws SQLException {

        if (resultSet.getString("cosmeticId") == null) throw new RuntimeException("Failed to load title cosmetic: " + resultSet.getString("cosmeticId")+ ". No cosmetic Id");
        else if (resultSet.getString("titleId") == null) throw new RuntimeException("Failed to load title cosmetic: " + resultSet.getString("cosmeticId")+ ". No title Id " + resultSet.getString("titleId"));
        else if (resultSet.getString("paintId") == null) throw new RuntimeException("Failed to load title cosmetic: " + resultSet.getString("cosmeticId")+ ". No paint Id " + resultSet.getString("paintId"));

        final Cosmetic t = Cosmetic.getCosmetic(resultSet.getString("titleId"));
        final Cosmetic p = Cosmetic.getCosmetic(resultSet.getString("paintId"));
        if (t == null && p == null) throw new RuntimeException("Failed to load title cosmetic: " + resultSet.getString("cosmeticId")+ ". This is not a good thing, but don't spam raymond because you probably messed with the db :DDDDDDDDDDDDDDDD");
        return new AssignedTitle(UUID.fromString(resultSet.getString("user_id")), resultSet.getString("cosmeticId"), t == null ? null : t.asTitle(), p == null ? null : p.asTitlePaint());
    }

    public String toSQL() {
        // dont need sql protection due to the uuid being a valid uuid, title is validated, paint is validated.
        return "INSERT INTO `titles` (`user_id`, `cosmeticId`, `titleId`, `paintId`) VALUES ('" + uuid.toString() + "', '" + cosmeticId + "', '" + title.getName() + "', '" + (paint == null ? "" : paint.getName()) + "') ON DUPLICATE KEY UPDATE `titleId` = '" + title.getName() + "', `paintId` = '" + (paint == null ? "" : paint.getName()) + "';";
    }

    

    @Override
    public ItemStack getItemStack() {
        return title.getItemStack();
    }

    @Override
    public String getName() {
        return cosmeticId;
    }

    @Override
    public CosmeticType getType() {
        return CosmeticType.ASSIGNED_TITLE;
    }
}
