package com.reflexian.levitycosmetics.utilities.serializers;

import com.reflexian.levitycosmetics.data.objects.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.titles.LTitle;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;

public class TitleSerializer extends Serializer<LTitle> {

    @Override
    protected void saveObject(String s, LTitle lTitle, BukkitConfiguration bukkitConfiguration) {
        // id, name, color, permission, itemstack
        bukkitConfiguration.set(s + ".id", lTitle.getId());
        bukkitConfiguration.set(s + ".name", lTitle.getName());
        bukkitConfiguration.set(s + ".tag", lTitle.getTag());
        bukkitConfiguration.set(s + ".permission", lTitle.getPermission());
        bukkitConfiguration.set(s + ".itemstack", lTitle.getItemStack());
    }

    @Override
    public LTitle deserialize(String s, BukkitConfiguration bukkitConfiguration) {

        return new LTitle(
                bukkitConfiguration.getString(s + ".id"),
                bukkitConfiguration.getString(s + ".name"),
                bukkitConfiguration.getString(s + ".tag"),
                bukkitConfiguration.getString(s + ".permission"),
                new ConfigItemSerializer().deserialize(s + ".itemstack", bukkitConfiguration)
        );
    }
}
