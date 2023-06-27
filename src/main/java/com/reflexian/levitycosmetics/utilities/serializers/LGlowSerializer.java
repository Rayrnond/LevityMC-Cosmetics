package com.reflexian.levitycosmetics.utilities.serializers;

import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LGlow;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;

public class LGlowSerializer extends Serializer<LGlow> {

    @Override
    protected void saveObject(String s, LGlow lGlow, BukkitConfiguration bukkitConfiguration) {
        bukkitConfiguration.set(s + ".name", lGlow.getName());
        bukkitConfiguration.set(s + ".color", lGlow.getColor());
        bukkitConfiguration.set(s + ".itemstack", lGlow.getItemStack());
    }

    @Override
    public LGlow deserialize(String s, BukkitConfiguration bukkitConfiguration) {

        try {
            LGlow glow = new LGlow(
                    bukkitConfiguration.getString(s + ".name"),
                    bukkitConfiguration.getString(s + ".color"),
                    new ItemStackSerializer().deserialize(s + ".itemstack", bukkitConfiguration)
            );
            Cosmetic.addCosmetic(glow);
            return glow;
        }catch (Exception e) {
            System.out.println("Failed to load glow cosmetic: " + s);
            e.printStackTrace();
            return null;
        }

    }
}
