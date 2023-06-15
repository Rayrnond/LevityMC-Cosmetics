package com.reflexian.levitycosmetics.utilities.uncategorizied;

import com.reflexian.levitycosmetics.data.objects.cosmetic.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

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
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        UserData userData = UserDataService.shared.retrieveUserFromCache(player.getUniqueId());
        if (params.equals("title")) {
            if (userData.getSelectedTitle() == null) return "";
            return GradientUtils.colorize(userData.getSelectedTitle().getTag().replace("%player%", player.getName()));
        } else if (params.equals("title_spaced")) {
            if (userData.getSelectedTitle() == null) return "";
            return GradientUtils.colorize(userData.getSelectedTitle().getTag().replace("%player%", player.getName() + " "));
        }
        return null;
    }
}
