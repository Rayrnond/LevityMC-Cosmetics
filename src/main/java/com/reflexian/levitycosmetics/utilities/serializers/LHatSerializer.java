package com.reflexian.levitycosmetics.utilities.serializers;

import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LHat;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;

public class LHatSerializer extends Serializer<LHat> {

    @Override
    protected void saveObject(String s, LHat lHat, BukkitConfiguration bukkitConfiguration) {
        bukkitConfiguration.set(s + ".name", lHat.getName());
        bukkitConfiguration.set(s + ".chatcolorChance", lHat.getChatcolorChance());
        bukkitConfiguration.set(s + ".tabcolorChance", lHat.getTabcolorChance());
        bukkitConfiguration.set(s + ".glowChance", lHat.getGlowChance());
        bukkitConfiguration.set(s + ".crownChance", lHat.getCrownChance());
        bukkitConfiguration.set(s + ".helmet", lHat.getHelmet());
        bukkitConfiguration.set(s + ".itemstack", lHat.getItemStack());
    }

    @Override
    public LHat deserialize(String s, BukkitConfiguration bukkitConfiguration) {

        try {
            LHat hat = new LHat(
                    bukkitConfiguration.getString(s + ".name"),
                    bukkitConfiguration.getInt(s + ".chatcolorChance"),
                    bukkitConfiguration.getInt(s + ".tabcolorChance"),
                    bukkitConfiguration.getInt(s + ".glowChance"),
                    bukkitConfiguration.getInt(s + ".crownChance"),
                    new ItemStackSerializer().deserialize(s + ".helmet", bukkitConfiguration),
                    new ItemStackSerializer().deserialize(s + ".itemstack", bukkitConfiguration)
            );

            if (bukkitConfiguration.contains(s + ".rarity")) {
                hat.setRarity(bukkitConfiguration.getInt(s + ".rarity"));
            }

            Cosmetic.addCosmetic(hat);
            return hat;
        }catch (Exception e) {
            System.out.println("Failed to load hat cosmetic: " + s);
            e.printStackTrace();
            return null;
        }
    }
}
