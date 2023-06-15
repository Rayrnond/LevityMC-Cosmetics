package com.reflexian.levitycosmetics.data.configs;

import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.Comment;
import pl.mikigal.config.annotation.ConfigName;
import pl.mikigal.config.annotation.ConfigPath;

@ConfigName("messages.yml")
public interface MessagesConfig extends Config {


    @Comment("The message sent to the player when they select a cosmetic. Use %cosmetic% for the cosmetic name.")
    @ConfigPath("backpack.selected")
    default String getBackpackSelectMessage() {
        return "&aYou have selected the %cosmetic% &acosmetic!";
    }


}
