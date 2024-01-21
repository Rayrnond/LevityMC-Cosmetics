package com.reflexian.levitycosmetics;

import com.reflexian.levitycosmetics.data.Database;
import com.reflexian.levitycosmetics.data.configs.DefaultConfig;
import com.reflexian.levitycosmetics.data.configs.MessagesConfig;
import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LChatColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.chatcolors.LTabColor;
import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LCrown;
import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LGlow;
import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.LHat;
import com.reflexian.levitycosmetics.data.objects.cosmetics.joinmessage.LJoinMessage;
import com.reflexian.levitycosmetics.data.objects.cosmetics.nickname.LNicknamePaint;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.LTitle;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.LTitlePaint;
import com.reflexian.levitycosmetics.data.objects.crates.CosmeticCrate;
import com.reflexian.levitycosmetics.listeners.ItemsAdderListener;
import com.reflexian.levitycosmetics.utilities.serializers.*;
import com.reflexian.levitycosmetics.utilities.uncategorizied.LevityPlaceholders;
import com.reflexian.rapi.RAPI;
import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mikigal.config.ConfigAPI;
import pl.mikigal.config.style.CommentStyle;
import pl.mikigal.config.style.NameStyle;

@Getter
public final class LevityCosmetics extends JavaPlugin {

    @Getter private static LevityCosmetics instance;

    private Economy economy;
    private DefaultConfig defaultConfig;
    private MessagesConfig messagesConfig;

    private InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        instance = this;

        if (!setupEconomy()) {
            getLogger().severe("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        ConfigAPI.registerSerializer(ItemStack.class, new ItemStackSerializer());
        ConfigAPI.registerSerializer(LChatColor.class, new LChatColorSerializer());
        ConfigAPI.registerSerializer(LGlow.class, new LGlowSerializer());
        ConfigAPI.registerSerializer(LTitle.class, new LTitleSerializer());
        ConfigAPI.registerSerializer(LTitlePaint.class, new LTitlePaintSerializer());
        ConfigAPI.registerSerializer(LCrown.class, new LCrownSerializer());
        ConfigAPI.registerSerializer(LTabColor.class, new LTabColorSerializer());
        ConfigAPI.registerSerializer(LNicknamePaint.class, new LNicknamePaintSerializer());
        ConfigAPI.registerSerializer(LJoinMessage.class, new LJoinMessageSerializer());
        ConfigAPI.registerSerializer(CosmeticCrate.class, new LCosmeticCrateSerializer());
        ConfigAPI.registerSerializer(LHat.class, new LHatSerializer());

        defaultConfig = ConfigAPI.init(DefaultConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, true, this);
        messagesConfig = ConfigAPI.init(MessagesConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, true, this);

        RAPI rapi = new RAPI(this);
        rapi.init();

        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        new LevityPlaceholders().register();

        // async
        Bukkit.getScheduler().runTaskAsynchronously(this, Database.shared::initializeDataSource);

        Bukkit.getScheduler().runTaskLater(this, ItemsAdderListener::onLoad, 20*5);

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
