package com.reflexian.levitycosmetics.utilities.serializers;

import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LCrown;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.cosmetics.joinmessage.LJoinMessage;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;

public class LJoinMessageSerializer extends Serializer<LJoinMessage> {

    @Override
    protected void saveObject(String s, LJoinMessage joinMessage, BukkitConfiguration bukkitConfiguration) {
        bukkitConfiguration.set(s + ".name", joinMessage.getName());
        bukkitConfiguration.set(s + ".message", joinMessage.getMessage());
        bukkitConfiguration.set(s + ".itemstack", joinMessage.getItemStack());
    }

    @Override
    public LJoinMessage deserialize(String s, BukkitConfiguration bukkitConfiguration) {

        try {
            LJoinMessage joinMessage = new LJoinMessage(
                    bukkitConfiguration.getString(s + ".name"),
                    bukkitConfiguration.getString(s + ".message"),
                    new ItemStackSerializer().deserialize(s + ".itemstack", bukkitConfiguration)
            );
            if (bukkitConfiguration.contains(s + ".rarity")) {
                joinMessage.setRarity(bukkitConfiguration.getInt(s + ".rarity"));
            }
            Cosmetic.addCosmetic(joinMessage);
            return joinMessage;
        }catch (Exception e) {
            System.out.println("Failed to load crown cosmetic: " + s);
            e.printStackTrace();
            return null;
        }

    }
}
