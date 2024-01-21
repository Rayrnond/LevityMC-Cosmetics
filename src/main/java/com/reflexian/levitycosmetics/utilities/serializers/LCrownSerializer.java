package com.reflexian.levitycosmetics.utilities.serializers;

import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LCrown;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;

public class LCrownSerializer extends Serializer<LCrown> {

    @Override
    protected void saveObject(String s, LCrown lCrown, BukkitConfiguration bukkitConfiguration) {
        bukkitConfiguration.set(s + ".name", lCrown.getName());
        bukkitConfiguration.set(s + ".symbol", lCrown.getSymbol());
        bukkitConfiguration.set(s + ".itemstack", lCrown.getItemStack());
    }

    @Override
    public LCrown deserialize(String s, BukkitConfiguration bukkitConfiguration) {

        try {
            LCrown crown = new LCrown(
                    bukkitConfiguration.getString(s + ".name"),
                    bukkitConfiguration.getString(s + ".symbol"),
                    new ItemStackSerializer().deserialize(s + ".itemstack", bukkitConfiguration)
            );
            if (bukkitConfiguration.contains(s + ".rarity")) {
                crown.setRarity(bukkitConfiguration.getInt(s + ".rarity"));
            }
            Cosmetic.addCosmetic(crown);
            return crown;
        }catch (Exception e) {
            System.out.println("Failed to load crown cosmetic: " + s);
            e.printStackTrace();
            return null;
        }

    }
}
