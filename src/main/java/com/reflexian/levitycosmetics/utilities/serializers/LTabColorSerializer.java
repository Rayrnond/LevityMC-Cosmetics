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

        try {
            LTabColor tabColor = new LTabColor(
                    bukkitConfiguration.getString(s + ".name"),
                    bukkitConfiguration.getString(s + ".color"),
                    new ItemStackSerializer().deserialize(s + ".itemstack", bukkitConfiguration)
            );
            if (bukkitConfiguration.contains(s + ".rarity")) {
                tabColor.setRarity(bukkitConfiguration.getInt(s + ".rarity"));
            }
            Cosmetic.addCosmetic(tabColor);
            return tabColor;
        }catch (Exception e) {
            System.out.println("Failed to load tab color cosmetic: " + s);
            e.printStackTrace();
            return null;
        }
    }
}
