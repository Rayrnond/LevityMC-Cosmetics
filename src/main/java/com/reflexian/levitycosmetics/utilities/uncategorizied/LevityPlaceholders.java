package com.reflexian.levitycosmetics.utilities.uncategorizied;

import com.reflexian.levitycosmetics.data.objects.user.UserData;
import com.reflexian.levitycosmetics.data.objects.user.UserDataService;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
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
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        UserData userData = UserDataService.shared.retrieveUserFromCache(player.getUniqueId());


        String beforeFormatted = "";
        switch (params) {
            case "title", "title_spaced" -> {
                if (userData.getSelectedTitle() != null) {
                    if (userData.getSelectedTitle().getPaint() != null) {
                        beforeFormatted = GradientUtils.stripColor(userData.getSelectedTitle().getTitle().getTag());
                        beforeFormatted = GradientUtils.colorize(userData.getSelectedTitle().getPaint().getColor().replace("%tag%", beforeFormatted));
                    } else beforeFormatted = userData.getSelectedTitle().getTitle().getTag();
                }
            }
            case "titlepaint", "titlepaint_spaced" -> {
                if (userData.getSelectedTitle() != null && userData.getSelectedTitle().getPaint() != null) {
                    beforeFormatted = GradientUtils.stripColor(userData.getSelectedTitle().getTitle().getTag());
                    beforeFormatted = GradientUtils.colorize(userData.getSelectedTitle().getPaint().getColor().replace("%tag%", beforeFormatted));
                } else if (userData.getSelectedTitle() != null) {
                    beforeFormatted = userData.getSelectedTitle().getTitle().getTag();
                    beforeFormatted = GradientUtils.colorize(beforeFormatted);
                }
            }
            case "tabcolor" -> {
                beforeFormatted = getTabFormatted(player, userData);
                beforeFormatted = GradientUtils.colorize(beforeFormatted);
            }
            case "chatcolor" -> {
                if (userData.getSelectedChatColor() != null) {
                    beforeFormatted = userData.getSelectedChatColor().getColor();
                    beforeFormatted = GradientUtils.colorize(beforeFormatted);
                }
            }
            case "crown", "crown_spaced" -> {
                if (userData.getSelectedCrown() != null) {
                    beforeFormatted = userData.getSelectedCrown().getSymbol();
                    beforeFormatted = GradientUtils.colorize(beforeFormatted);
                }
            }
            case "glowcolor" -> {
                if (userData.getGlow() != null) {
                    return String.valueOf(userData.getGlow().getColor());
                } else {
                    return "";
                }
            }
            case "nickname" -> {
                beforeFormatted = getFormattedName(player, userData, true);
                beforeFormatted = GradientUtils.colorize(beforeFormatted);
            }
            case "joinmessage" -> {
                if (userData.getSelectedJoinMessage() != null) {
                    beforeFormatted = userData.getSelectedJoinMessage().getMessage().replace("%player%", player.getName()).replace("%playerWithColor%", GradientUtils.colorize(getFormattedName(player, userData, true)));
                    beforeFormatted = GradientUtils.colorize(beforeFormatted);
                }
            }
        }
        if (beforeFormatted.length() != 0 && params.endsWith("_spaced")) beforeFormatted = beforeFormatted + " ";
        beforeFormatted = beforeFormatted.replace("%player%", getFormattedName(player, userData, false));
        return beforeFormatted;
    }

    private String getFormattedName(Player player, UserData userData, boolean withNick) {
        String username;
        if (userData.getSelectedNickname() != null && withNick) {
            username = userData.getSelectedNickname().getContent();
            if (userData.getSelectedNickname().getPaint() != null) {
                username = "~"+userData.getSelectedNickname().getPaint().getColor().replace("%player%", username);
            }
        } else {
            username = player.getName();
        }
        return username;
    }

    private String getTabFormatted(Player player, UserData userData) {
        String username = getFormattedName(player, userData, false);
        if (userData.getSelectedTabColor() != null) {
            username = userData.getSelectedTabColor().getColor().replace("%player%", username);
        }
        return username;
    }

}
