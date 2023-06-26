package com.reflexian.levitycosmetics.utilities.uncategorizied;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@Builder@Setter@Getter
public class Glow {
    private String name;
    private Player player;
    private ChatColor color;

    private final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

    public void apply() {
        if (player == null) return;
        if (color == null) return;
        if (name == null) return;

        player.setScoreboard(scoreboard);
        Team team = scoreboard.getTeam(color+"team") == null ? scoreboard.registerNewTeam(color+"team") : scoreboard.getTeam(color+"team");
        team.setColor(color);
        team.addEntry(player.getName());
        player.setGlowing(true);
    }

    public void destroy() {
        player.removePotionEffect(org.bukkit.potion.PotionEffectType.GLOWING);
        player.setGlowing(false);
        Scoreboard scoreboard = player.getScoreboard();
        scoreboard.getTeam(color+"team").removeEntry(player.getName());
    }
}
