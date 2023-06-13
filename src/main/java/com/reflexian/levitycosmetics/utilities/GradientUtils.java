package com.reflexian.levitycosmetics.utilities;

import com.reflexian.levitycosmetics.data.inventories.ChatColorInventory;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradientUtils {
    final static Pattern pattern = Pattern.compile("<#([0-9a-fA-F]{3}|[0-9a-fA-F]{6})>");
    public static String colorize(String string) {
        string = org.bukkit.ChatColor.translateAlternateColorCodes('&', string);
        String[] hexStrings = extractHexStrings(string);
        Matcher matcher = pattern.matcher(string);
        string = matcher.replaceAll("");

        if (hexStrings[0] != null  && string.length() == 1) {
            string = ChatColor.of(hexStrings[0]) + string;
        } else {
            if (hexStrings[0] != null && hexStrings[1] != null) {
                string = createGradient(string, hexStrings[0], hexStrings[1]);
            } else if (hexStrings[0]!=null) {
                string = ChatColor.of(hexStrings[0]) + string;
            }
        }

        return string;
    }

    public static String[] extractHexStrings(String input) {
        Matcher matcher = pattern.matcher(input);
        String[] hexStrings = new String[2];
        int index = 0;

        while (matcher.find() && index < 2) {
            String hexString = matcher.group(1);
            hexString = "#" + hexString;
            hexStrings[index] = hexString;
            index++;
        }

        return hexStrings;
    }

    public static String createGradient(String text, String startColor, String endColor) {
        // Convert hex colors to ChatColor
        ChatColor startChatColor = ChatColor.of(startColor);
        ChatColor endChatColor = ChatColor.of(endColor);

        // Calculate the color gradient
        int textLength = text.length();
        double colorStep = 1.0 / (textLength - 1);
        StringBuilder gradientBuilder = new StringBuilder();

        for (int i = 0; i < textLength; i++) {
            double ratio = colorStep * i;
            ChatColor interpolatedColor = interpolateColor(startChatColor, endChatColor, ratio);
            gradientBuilder.append(interpolatedColor).append(text.charAt(i));
        }

        return gradientBuilder.toString();
    }

    private static ChatColor interpolateColor(ChatColor startColor, ChatColor endColor, double ratio) {
        // Interpolate the color components
        int red = interpolateComponent(startColor.getColor().getRed(), endColor.getColor().getRed(), ratio);
        int green = interpolateComponent(startColor.getColor().getGreen(), endColor.getColor().getGreen(), ratio);
        int blue = interpolateComponent(startColor.getColor().getBlue(), endColor.getColor().getBlue(), ratio);

        // Create the interpolated ChatColor
        return ChatColor.of(new java.awt.Color(red, green, blue));
    }

    private static int interpolateComponent(int start, int end, double ratio) {
        // Interpolate a single color component
        int interpolatedComponent = (int) (start + (end - start) * ratio);
        return Math.min(255, Math.max(0, interpolatedComponent));
    }
}