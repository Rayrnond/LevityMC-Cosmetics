package com.reflexian.levitycosmetics.utilities.serializers;

import com.reflexian.levitycosmetics.data.objects.chatcolors.LChatColor;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;

public class ChatColorSerializer extends Serializer<LChatColor> {

    @Override
    protected void saveObject(String s, LChatColor lChatColor, BukkitConfiguration bukkitConfiguration) {
        // id, name, color, permission, itemstack
        bukkitConfiguration.set(s + ".id", lChatColor.getId());
        bukkitConfiguration.set(s + ".name", lChatColor.getName());
        bukkitConfiguration.set(s + ".color", lChatColor.getColor());
        bukkitConfiguration.set(s + ".permission", lChatColor.getPermission());
        bukkitConfiguration.set(s + ".itemstack", lChatColor.getItemStack());
    }

    @Override
    public LChatColor deserialize(String s, BukkitConfiguration bukkitConfiguration) {

        return new LChatColor(
                bukkitConfiguration.getString(s + ".id"),
                bukkitConfiguration.getString(s + ".name"),
                bukkitConfiguration.getString(s + ".color"),
                bukkitConfiguration.getString(s + ".permission"),
                new ConfigItemSerializer().deserialize(s + ".itemstack", bukkitConfiguration)
        );
    }
}