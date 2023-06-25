package com.reflexian.levitycosmetics.utilities.serializers;

import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.cosmetics.nickname.LNicknamePaint;
import com.reflexian.levitycosmetics.data.objects.crates.CosmeticCrate;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;

public class LCosmeticCrateSerializer extends Serializer<CosmeticCrate> {

    @Override
    protected void saveObject(String s, CosmeticCrate crate, BukkitConfiguration bukkitConfiguration) {
        bukkitConfiguration.set(s + ".name", crate.getName());
        bukkitConfiguration.set(s + ".itemstack", crate.getItemStack());
        bukkitConfiguration.set(s + ".cosmetics", crate.getPotentialCosmetics());
    }

    @Override
    public CosmeticCrate deserialize(String s, BukkitConfiguration bukkitConfiguration) {

        CosmeticCrate crate = new CosmeticCrate(
                bukkitConfiguration.getString(s + ".name"),
                new ItemStackSerializer().deserialize(s + ".itemstack", bukkitConfiguration),
                bukkitConfiguration.getStringList(s + ".cosmetics")
        );
        CosmeticCrate.CRATES.put(crate.getName(), crate);
        return crate;
    }
}
