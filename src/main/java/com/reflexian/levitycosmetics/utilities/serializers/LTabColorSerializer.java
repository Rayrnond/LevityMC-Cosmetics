package com.reflexian.levitycosmetics.utilities.serializers;

import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LTabColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;

public class LTabColorSerializer extends Serializer<LTabColor> {

    @Override
    protected void saveObject(String s, LTabColor lTabColor, BukkitConfiguration bukkitConfiguration) {
        bukkitConfiguration.set(s + ".name", lTabColor.getName());
        bukkitConfiguration.set(s + ".color", lTabColor.getColor());
        bukkitConfiguration.set(s + ".itemstack", lTabColor.getItemStack());
    }

    @Override
    public LTabColor deserialize(String s, BukkitConfiguration bukkitConfiguration) {

        LTabColor tabColor = new LTabColor(
                bukkitConfiguration.getString(s + ".name"),
                bukkitConfiguration.getString(s + ".color"),
                new ItemStackSerializer().deserialize(s + ".itemstack", bukkitConfiguration)
        );
        Cosmetic.addCosmetic(tabColor);
        return tabColor;
    }
}
