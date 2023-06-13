package com.reflexian.levitycosmetics.data.objects.cosmetic;

import com.reflexian.levitycosmetics.data.objects.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.titles.LTitle;
import com.reflexian.levitycosmetics.data.objects.user.UserData;

import java.util.HashSet;
import java.util.Set;

public abstract class Cosmetic {


    private static final Set<Cosmetic> cosmetics = new HashSet<>();
    public static Set<Cosmetic> getAllCosmetics() {
        return cosmetics;
    }
    public static void addCosmetic(Cosmetic cosmetic) {
        cosmetics.add(cosmetic);
    }
    public static Cosmetic getCosmetic(String id) {
        return cosmetics.stream().filter(cosmetic -> cosmetic.getUniqueId().equals(id)).findFirst().orElse(null);
    }
    public static void removeCosmetic(Cosmetic cosmetic) {
        cosmetics.remove(cosmetic);
    }

    public Cosmetic() {
        cosmetics.add(this);
    }

    public abstract String getUniqueId();

    public void giveToUser(UserData userData) {
        if (!userData.getCosmeticIds().contains(getUniqueId())) {
            userData.getCosmeticIds().add(getUniqueId());
        }
    }

    public void removeFromUser(UserData userData) {
        userData.getCosmeticIds().remove(getUniqueId());
    }


    public LChatColor asChatColor() {
        return (LChatColor) this;
    }
    public LTitle asTitle() {
        return (LTitle) this;
    }
}
