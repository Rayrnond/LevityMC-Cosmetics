package com.reflexian.levitycosmetics.utilities.serializers;

import com.reflexian.levitycosmetics.data.objects.crates.CosmeticCrate;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;
import pl.mikigal.config.serializer.Serializers;

import java.util.List;

public class LCosmeticCrateSerializer extends Serializer<CosmeticCrate> {

    @Override
    protected void saveObject(String s, CosmeticCrate crate, BukkitConfiguration bukkitConfiguration) {
        bukkitConfiguration.set(s + ".name", crate.getName());
        bukkitConfiguration.set(s + ".itemstack", crate.getItemStack());
        bukkitConfiguration.set(s + ".cosmetics", crate.getPotentialCosmetics());
    }

    @Override
    public CosmeticCrate deserialize(String s, BukkitConfiguration bukkitConfiguration) {

        try {
            CosmeticCrate crate = new CosmeticCrate(
                    bukkitConfiguration.getString(s + ".name"),
                    new ItemStackSerializer().deserialize(s + ".itemstack", bukkitConfiguration),
                    Serializers.of(List.class).deserialize(s + ".cosmetics", bukkitConfiguration)
            );
            CosmeticCrate.CRATES.put(crate.getName(), crate);
            return crate;
        }catch (Exception e) {
            System.out.println("Failed to load crate cosmetic: " + s);
            e.printStackTrace();
            return null;
        }


    }
}
