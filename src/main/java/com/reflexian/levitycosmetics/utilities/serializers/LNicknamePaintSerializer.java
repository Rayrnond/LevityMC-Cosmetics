package com.reflexian.levitycosmetics.utilities.serializers;

import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.cosmetics.nickname.LNicknamePaint;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;

public class LNicknamePaintSerializer extends Serializer<LNicknamePaint> {

    @Override
    protected void saveObject(String s, LNicknamePaint lTitle, BukkitConfiguration bukkitConfiguration) {
        bukkitConfiguration.set(s + ".name", lTitle.getName());
        bukkitConfiguration.set(s + ".color", lTitle.getColor());
        bukkitConfiguration.set(s + ".itemstack", lTitle.getItemStack());
    }

    @Override
    public LNicknamePaint deserialize(String s, BukkitConfiguration bukkitConfiguration) {

        try {
            LNicknamePaint paint = new LNicknamePaint(
                    bukkitConfiguration.getString(s + ".name"),
                    bukkitConfiguration.getString(s + ".color"),
                    new ItemStackSerializer().deserialize(s + ".itemstack", bukkitConfiguration)
            );
            if (bukkitConfiguration.contains(s + ".rarity")) {
                paint.setRarity(bukkitConfiguration.getInt(s + ".rarity"));
            }
            Cosmetic.addCosmetic(paint);
            return paint;
        }catch (Exception e) {
            System.out.println("Failed to load nickname paint cosmetic: " + s);
            e.printStackTrace();
            return null;
        }

    }
}
