package com.reflexian.levitycosmetics.data.objects.cosmetics.helpers;

import com.reflexian.levitycosmetics.data.Database;
import com.reflexian.levitycosmetics.data.objects.cosmetics.CosmeticType;
import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LTabColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.AssignedHat;
import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LCrown;
import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LGlow;
import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LHat;
import com.reflexian.levitycosmetics.data.objects.cosmetics.nickname.AssignedNickname;
import com.reflexian.levitycosmetics.data.objects.cosmetics.nickname.LNicknamePaint;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.AssignedTitle;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.LTitle;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.LTitlePaint;
import com.reflexian.levitycosmetics.data.objects.user.UserCosmetic;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class Cosmetic {

    private static final Set<Cosmetic> cosmetics = new HashSet<>();
    public static Set<Cosmetic> getAllCosmetics() {
        return cosmetics;
    }
    public static void addCosmetic(Cosmetic cosmetic) {
        if (cosmetics.stream().anyMatch(e->e.getName().equals(cosmetic.getName()))) {
            throw new RuntimeException("Failed to load cosmetics! Cosmetic with name " + cosmetic.getName() + " already exists!");
        }
        cosmetics.add(cosmetic);
    }
    public static Cosmetic getCosmetic(String id) {
        return cosmetics.stream().filter(cosmetic -> cosmetic.getName().equals(id)).findFirst().orElse(null);
    }

    public Cosmetic() {}

    public abstract ItemStack getItemStack();
    public abstract String getName();
    public abstract CosmeticType getType();

    public void giveToUser(UserData userData) {

        if (this instanceof LTitle) {
            AssignedTitle assignedTitle = new AssignedTitle(userData.getUuid(), new Random().nextInt(32393)+userData.getUuid().toString().substring(0,10).replace("-",""), (LTitle) this, null);
            userData.getAssignedTitles().add(assignedTitle);
            Database.shared.save(assignedTitle);
            UserCosmetic userCosmetic = new UserCosmetic(userData.getUuid(), assignedTitle.getCosmeticId(), assignedTitle.getCosmeticId(), CosmeticType.ASSIGNED_TITLE);
            userData.getUserCosmetics().add(userCosmetic);
            return;
        } else if (this instanceof LHat) {
            AssignedHat assignedHat = new AssignedHat(userData.getUuid(), new Random().nextInt(32393)+userData.getUuid().toString().substring(0,13).replace("-",""));
            assignedHat.setLHat((LHat) this);

            if (runProbability(assignedHat.getLHat().getGlowChance())) {
                Cosmetic random = random(CosmeticType.GLOW);
                if (random instanceof LGlow) {
                    assignedHat.setGlow(random.asGlow());
                }
            }
            if (runProbability(assignedHat.getLHat().getCrownChance())) {
                Cosmetic random = random(CosmeticType.CROWN);
                if (random instanceof LCrown) {
                    assignedHat.setCrown(random.asCrown());
                }
            }
            if (runProbability(assignedHat.getLHat().getChatcolorChance())) {
                Cosmetic random = random(CosmeticType.CHAT_COLOR);
                if (random instanceof LChatColor) {
                    assignedHat.setChatColor(random.asChatColor());
                }
            }
            if (runProbability(assignedHat.getLHat().getTabcolorChance())) {
                Cosmetic random = random(CosmeticType.TAB_COLOR);
                if (random instanceof LTabColor) {
                    assignedHat.setTabColor(random.asTabColor());
                }
            }
            userData.getAssignedHats().add(assignedHat);
            Database.shared.save(assignedHat);

            UserCosmetic userCosmetic = new UserCosmetic(userData.getUuid(), assignedHat.getCosmeticId(), assignedHat.getCosmeticId(), CosmeticType.ASSIGNED_HAT);
            userData.getUserCosmetics().add(userCosmetic);
            return;
        }

        userData.getUserCosmetics().add(UserCosmetic.fromCosmetic(userData.getUuid(), this));
    }


    public LChatColor asChatColor() {
        return (LChatColor) this;
    }
    public LTitle asTitle() {
        return (LTitle) this;
    }
    public LTitlePaint asTitlePaint() {
        return (LTitlePaint) this;
    }
    public AssignedHat asHat() {
        return (AssignedHat) this;
    }
    public LHat asLHat() {
        return (LHat) this;
    }
    public LGlow asGlow() {
        return (LGlow) this;
    }
    public LCrown asCrown() {
        return (LCrown) this;
    }
    public LTabColor asTabColor() {
        return (LTabColor) this;
    }
    public LNicknamePaint asNicknamePaint() {
        return (LNicknamePaint) this;
    }

    private boolean runProbability(int percent) {
        if (percent == 0) return false;
        boolean a = new Random().nextInt(100) < percent;
        return a;
    }

    private Cosmetic random(CosmeticType type) {
        List<Cosmetic> cosmetics = getAllCosmetics().stream()
                .filter(e -> e.getType().equals(type))
                .toList();

        if (cosmetics.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int index = random.nextInt(cosmetics.size());

        return cosmetics.get(index);
    }
}
