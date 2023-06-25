package com.reflexian.levitycosmetics.utilities.serializers;

import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.LTitle;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;

public class LTitleSerializer extends Serializer<LTitle> {

    @Override
    protected void saveObject(String s, LTitle lTitle, BukkitConfiguration bukkitConfiguration) {
        bukkitConfiguration.set(s + ".name", lTitle.getName());
        bukkitConfiguration.set(s + ".tag", lTitle.getTag());
        bukkitConfiguration.set(s + ".itemstack", lTitle.getItemStack());
    }

    @Override
    public LTitle deserialize(String s, BukkitConfiguration bukkitConfiguration) {

        LTitle title = new LTitle(
                bukkitConfiguration.getString(s + ".name"),
                bukkitConfiguration.getString(s + ".tag"),
                new ItemStackSerializer().deserialize(s + ".itemstack", bukkitConfiguration)
        );
        Cosmetic.addCosmetic(title);
        return title;
    }
}
