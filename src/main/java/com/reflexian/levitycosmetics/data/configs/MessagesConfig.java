package com.reflexian.levitycosmetics.data.configs;

import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.ConfigName;
import pl.mikigal.config.annotation.ConfigPath;

@ConfigName("messages.yml")
public interface MessagesConfig extends Config {


    @ConfigPath("mysql.host")
    default String getDatabaseHost() {
        return "127.0.0.1";
    }


}
