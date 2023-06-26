package com.reflexian.levitycosmetics.utilities.serializers;

import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LHat;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;

public class LHatSerializer extends Serializer<LHat> {

    @Override
    protected void saveObject(String s, LHat lHat, BukkitConfiguration bukkitConfiguration) {
        bukkitConfiguration.set(s + ".name", lHat.getName());
        bukkitConfiguration.set(s + ".chatcolor", lHat.getChatColor());
        bukkitConfiguration.set(s + ".tabcolor", lHat.getTabColor());
        bukkitConfiguration.set(s + ".glow", lHat.getGlow());
        bukkitConfiguration.set(s + ".crown", lHat.getCrown());
        bukkitConfiguration.set(s + ".helmet", lHat.getHelmet());
        bukkitConfiguration.set(s + ".itemstack", lHat.getItemStack());
    }

    @Override
    public LHat deserialize(String s, BukkitConfiguration bukkitConfiguration) {

        LHat hat = new LHat(
                bukkitConfiguration.getString(s + ".name"),
                bukkitConfiguration.getString(s + ".chatcolor"),
                bukkitConfiguration.getString(s + ".tabcolor"),
                bukkitConfiguration.getString(s + ".glow"),
                bukkitConfiguration.getString(s + ".crown"),
                new ItemStackSerializer().deserialize(s + ".helmet", bukkitConfiguration),
                new ItemStackSerializer().deserialize(s + ".itemstack", bukkitConfiguration)
        );

        Cosmetic.addCosmetic(hat);
        return hat;
    }
}
