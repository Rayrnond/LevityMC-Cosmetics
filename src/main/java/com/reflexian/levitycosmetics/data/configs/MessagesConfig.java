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
        return "<color:green>You have selected the %cosmetic% <color:green>cosmetic!";
    }

    @Comment("The message shown under an item's lore if the cosmetic is selected.")
    @ConfigPath("backpack.item-selected")
    default String getBackpackItemSelectedMessage() {
        return "<color:green>Already Selected!";
    }

    @Comment("The message shown when players reset their cosmetics.")
    @ConfigPath("backpack.reset")
    default String getBackpackResetMessage() {
        return "<color:red>You have reset all selected cosmetics!";
    }

    @Comment("The message shown when giving a nickname ticket.")
    @ConfigPath("nicknameticket.success")
    default String getNicknameGivenMessage() {
        return "<color:green>You have given %player% a nickname ticket!";
    }

    @Comment("The message shown when giving a nickname ticket.")
    @ConfigPath("nicknameticket.changed")
    default String getNicknameChangedMessage() {
        return "<color:green>You have added a custom nickname %nick%!";
    }

    @Comment("The message shown when giving a nickname ticket.")
    @ConfigPath("nicknameticket.error")
    default String getNicknameErrorMessage() {
        return "<color:red>Your nickname should be a-zA-z0-9_ and between 3 and 16 characters long!";
    }

    @Comment("The message shown when giving a nickname ticket.")
    @ConfigPath("nicknameticket.title")
    default String getNicknameTitleMessage() {
        return "<color:green>Type your new nickname in chat. Type 'cancel' to cancel.";
    }

    @Comment("The message shown in chat when starting a drop")
    @ConfigPath("drop.start")
    default String getDropStartMessage() {
        return "<color:green>A cosmetic drop has started! %cosmetic% is now for sale for only %price% credits, only x%amount% are in stock!\n<bold><color:yellow>Run `/drop purchase` to buy!";
    }

    @Comment("The message shown in chat when a drop is ongoing")
    @ConfigPath("drop.ongoing")
    default String getDropOngoing() {
        return "<color:green>A cosmetic drop is in progress! %cosmetic% is now for sale for only %price% credits, only x%amount% are in stock!\n<bold><color:yellow>Run `/drop purchase` to buy!";
    }

    @Comment("The message shown in chat when a drop has ended")
    @ConfigPath("drop.ended")
    default String getDropEnded() {
        return "<color:green>A cosmetic drop has ended! %cosmetic% is no longer for sale!";
    }


}
