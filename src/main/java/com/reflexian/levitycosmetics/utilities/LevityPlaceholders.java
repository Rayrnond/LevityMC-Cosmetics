package com.reflexian.levitycosmetics.utilities;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LevityPlaceholders extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "levitycosmetics";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Raymond";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.00.00";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        switch (params) {
            case "test":
                return "test";
        }
        return null;
    }
}
