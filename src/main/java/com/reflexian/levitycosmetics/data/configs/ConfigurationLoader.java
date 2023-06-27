package com.reflexian.levitycosmetics.data.configs;

import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.configs.cosmetic.*;
import lombok.experimental.UtilityClass;
import pl.mikigal.config.ConfigAPI;
import pl.mikigal.config.style.CommentStyle;
import pl.mikigal.config.style.NameStyle;

import java.io.File;

@UtilityClass
public class ConfigurationLoader {

    public static ChatColorConfig CHAT_COLOR_CONFIG;
    public static TitleConfig TITLE_CONFIG;
    public static GUIConfig GUI_CONFIG;
    public static CrownConfig CROWN_CONFIG;
    public static GlowConfig GLOW_CONFIG;
    public static TabColorConfig TAB_COLOR_CONFIG;
    public static CrateConfig CRATE_CONFIG;
    public static NicknamePaintConfig NICKNAME_PAINT_CONFIG;
    public static HatConfig HAT_CONFIG;


    public static void init() {
        var instance = LevityCosmetics.getInstance();
        try {
            CHAT_COLOR_CONFIG = ConfigAPI.init(ChatColorConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, false, new File(instance.getDataFolder()+"/cosmetics/"), instance);
            TITLE_CONFIG = ConfigAPI.init(TitleConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, false, new File(instance.getDataFolder()+"/cosmetics/"), instance);
            GUI_CONFIG = ConfigAPI.init(GUIConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, false, instance);
            CROWN_CONFIG = ConfigAPI.init(CrownConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, false, new File(instance.getDataFolder()+"/cosmetics/"), instance);
            GLOW_CONFIG = ConfigAPI.init(GlowConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, false, new File(instance.getDataFolder()+"/cosmetics/"), instance);
            TAB_COLOR_CONFIG = ConfigAPI.init(TabColorConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, false, new File(instance.getDataFolder()+"/cosmetics/"), instance);
            CRATE_CONFIG = ConfigAPI.init(CrateConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, false, new File(instance.getDataFolder()+"/cosmetics/"), instance);
            NICKNAME_PAINT_CONFIG = ConfigAPI.init(NicknamePaintConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, false, new File(instance.getDataFolder()+"/cosmetics/"), instance);
            HAT_CONFIG = ConfigAPI.init(HatConfig.class, NameStyle.UNDERSCORE, CommentStyle.ABOVE_CONTENT, false, new File(instance.getDataFolder()+"/cosmetics/"), instance);
        }catch (Exception e) {
            LevityCosmetics.getInstance().getLogger().severe("--START ERROR -- Failed to load cosmetics config files! Disabling plugin.");
            e.printStackTrace();
            LevityCosmetics.getInstance().getLogger().severe("--END ERROR -- Failed to load cosmetics config files! Disabling plugin.");
            instance.getServer().getPluginManager().disablePlugin(instance);
        }
    }

}
