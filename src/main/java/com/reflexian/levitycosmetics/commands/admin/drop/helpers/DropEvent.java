package com.reflexian.levitycosmetics.commands.admin.drop.helpers;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.commands.admin.drop.DropParentCommand;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.crates.CosmeticCrate;
import com.reflexian.levitycosmetics.utilities.uncategorizied.GradientUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class DropEvent extends BukkitRunnable {

    private List<UUID> alreadyPurchased = new ArrayList<>();


    private Cosmetic cosmetic;
    private CosmeticCrate cosmeticCrate;

    private final boolean isCrate;


    private final int amount;
    private final int startingCost;

    private int currentCost;
    @Setter
    private int currentAmount;

    public DropEvent(Cosmetic cosmetic, int amount, int startingCost) {
        this.cosmetic = cosmetic;
        this.amount = amount;
        this.startingCost = startingCost;
        this.currentCost = startingCost;
        this.currentAmount = amount;
        this.isCrate = false;
    }
    public DropEvent(CosmeticCrate cosmeticCrate, int amount, int startingCost) {
        this.cosmeticCrate = cosmeticCrate;
        this.amount = amount;
        this.startingCost = startingCost;
        this.currentCost = startingCost;
        this.currentAmount = amount;
        this.isCrate = true;
    }

    private BukkitRunnable priceRunnable;

    @Override
    public void run() {

        if (startingCost == currentCost) {
            Bukkit.broadcastMessage(format(LevityCosmetics.getInstance().getMessagesConfig().getDropStartMessage()));
            priceRunnable = new BukkitRunnable() {
                @Override
                public void run() {
                    currentCost-=1;
                    if (currentCost == 1 || currentAmount == 0) {
                        priceRunnable.cancel();
                    }
                }
            };
            priceRunnable.runTaskTimer(LevityCosmetics.getInstance(), 0, 20);


        } else if (currentCost > 1 && currentAmount > 0) {
            Bukkit.broadcastMessage(format(LevityCosmetics.getInstance().getMessagesConfig().getDropOngoing()));
        }  else {
            Bukkit.broadcastMessage(format(LevityCosmetics.getInstance().getMessagesConfig().getDropEnded()));
            cancel();
            priceRunnable.cancel();
            DropParentCommand.EVENT = null;
        }


        // send message every 10 seconds
        // if price reaches 0, stop
        //price subtracts 1 every second

        //%cosmetic% %price% %amount% %cost%


    }


    private String format(String s){
        s=s.replace("%cosmetic%", isCrate ? cosmeticCrate.getName() : cosmetic.getName())
                .replace("%price%", String.valueOf(currentCost))
                .replace("%amount%", String.valueOf(currentAmount))
                .replace("%cost%", String.valueOf(startingCost));
        return GradientUtils.colorize(s);
    }
}
