package com.reflexian.levitycosmetics.utilities.serializers;

import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;

public class LChatColorSerializer extends Serializer<LChatColor> {

    @Override
    protected void saveObject(String s, LChatColor lChatColor, BukkitConfiguration bukkitConfiguration) {
        bukkitConfiguration.set(s + ".name", lChatColor.getName());
        bukkitConfiguration.set(s + ".color", lChatColor.getColor());
        bukkitConfiguration.set(s + ".itemstack", lChatColor.getItemStack());
    }

    @Override
    public LChatColor deserialize(String s, BukkitConfiguration bukkitConfiguration) {

        LChatColor chatColor = new LChatColor(
                bukkitConfiguration.getString(s + ".name"),
                bukkitConfiguration.getString(s + ".color"),
                new ItemStackSerializer().deserialize(s + ".itemstack", bukkitConfiguration)
        );
        Cosmetic.addCosmetic(chatColor);
        return chatColor;
    }
}
