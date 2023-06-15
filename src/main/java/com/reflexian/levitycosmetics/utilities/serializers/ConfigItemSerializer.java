package com.reflexian.levitycosmetics.utilities.serializers;

import com.reflexian.levitycosmetics.utilities.uncategorizied.GradientUtils;
import com.reflexian.levitycosmetics.utilities.uncategorizied.ItemBuilder;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;
import pl.mikigal.config.serializer.Serializers;

import java.util.List;

public class ConfigItemSerializer extends Serializer<ItemStack> {
    @Override
    protected void saveObject(String s, ItemStack itemStack, BukkitConfiguration bukkitConfiguration) {
        bukkitConfiguration.set(s + ".material", itemStack.getType().name());
        bukkitConfiguration.set(s + ".data", itemStack.getData().getData());
        bukkitConfiguration.set(s + ".amount", itemStack.getAmount());
        bukkitConfiguration.set(s + ".displayname", itemStack.getItemMeta().getDisplayName());
        if (itemStack.getItemMeta().getLore() != null) {
            bukkitConfiguration.set(s + ".lore", itemStack.getItemMeta().getLore());
        }
        bukkitConfiguration.set(s + ".glow", itemStack.getEnchantments().size()!=0);
        bukkitConfiguration.set(s + ".itemsadderID", "");
    }

    @Override
    public ItemStack deserialize(String s, BukkitConfiguration bukkitConfiguration) {

        ItemBuilder builder = new ItemBuilder(Material.valueOf(bukkitConfiguration.getString(s + ".material").toUpperCase()));


        if (bukkitConfiguration.contains(s + ".itemsadderID")) {
            String id = bukkitConfiguration.getString(s + ".itemsadderID");
            if (id != null && !id.isEmpty()) {
                CustomStack stack = CustomStack.getInstance(id);
                if (stack == null) {
                    throw new RuntimeException("Failed to serialize item with ID: " + s + ". Could not find ItemsAdder item with id " + id);
                }

                builder = new ItemBuilder(stack.getItemStack());
            }
        }

        if (bukkitConfiguration.contains(s + ".data")) builder.data(new MaterialData(builder.getMaterial(), (byte) bukkitConfiguration.getInt(s + ".data")));
        if (bukkitConfiguration.contains(s + ".amount")) builder.amount(bukkitConfiguration.getInt(s + ".amount"));
        if (bukkitConfiguration.contains(s + ".displayname")) builder.displayname(bukkitConfiguration.getString(s + ".displayname"));
        if (bukkitConfiguration.contains(s + ".lore")) {
            List<String> lore = Serializers.of(List.class).deserialize(s + ".lore", bukkitConfiguration);
            for (String s1 : lore) {
                builder.lore(GradientUtils.colorize(s1));
            }

        }
        if (bukkitConfiguration.getBoolean(s + ".glow")) builder.glow();
        return builder.build();
    }
}
