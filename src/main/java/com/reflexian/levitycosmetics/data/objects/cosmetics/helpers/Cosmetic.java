package com.reflexian.levitycosmetics.data.objects.cosmetics.helpers;

import com.reflexian.levitycosmetics.data.Database;
import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LTabColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LCrown;
import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LGlow;
import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LHat;
import com.reflexian.levitycosmetics.data.objects.cosmetics.nickname.LNicknamePaint;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.AssignedTitle;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.LTitle;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.LTitlePaint;
import com.reflexian.levitycosmetics.data.objects.user.UserData;
import net.kyori.adventure.title.Title;
import org.bukkit.inventory.ItemStack;

import javax.xml.crypto.Data;
import java.util.HashSet;
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
    public static void removeCosmetic(Cosmetic cosmetic) {
        cosmetics.remove(cosmetic);
    }

    public Cosmetic() {}

    public abstract ItemStack getItemStack();
    public abstract String getName();

    public void giveToUser(UserData userData) {
        if (this instanceof LTitle && !userData.getAssignedTitles().stream().anyMatch(e->e.getTitle() == this)) {
            AssignedTitle assignedTitle = new AssignedTitle(userData.getUuid(), new Random().nextInt(3293)+userData.getUuid().toString().substring(0,10).replace("-",""), (LTitle) this, null);
            userData.getAssignedTitles().add(assignedTitle);
            Database.shared.save(assignedTitle);
            return;
        }
        userData.getAllCosmetics().add(this);
    }

    public void removeFromUser(UserData userData) {
        userData.getAllCosmetics().remove(this);
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
    public LHat asHat() {
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

}
