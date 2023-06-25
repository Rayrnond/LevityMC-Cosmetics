package com.reflexian.levitycosmetics.utilities.serializers;

import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.LTitlePaint;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;

public class LTitlePaintSerializer extends Serializer<LTitlePaint> {

    @Override
    protected void saveObject(String s, LTitlePaint lTitle, BukkitConfiguration bukkitConfiguration) {
        bukkitConfiguration.set(s + ".name", lTitle.getName());
        bukkitConfiguration.set(s + ".color", lTitle.getColor());
        bukkitConfiguration.set(s + ".itemstack", lTitle.getItemStack());
    }

    @Override
    public LTitlePaint deserialize(String s, BukkitConfiguration bukkitConfiguration) {

        LTitlePaint title = new LTitlePaint(
                bukkitConfiguration.getString(s + ".name"),
                bukkitConfiguration.getString(s + ".color"),
                new ItemStackSerializer().deserialize(s + ".itemstack", bukkitConfiguration)
        );
        Cosmetic.addCosmetic(title);
        return title;
    }
}
