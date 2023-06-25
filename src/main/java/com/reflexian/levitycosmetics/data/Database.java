package com.reflexian.levitycosmetics.data;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.configs.DefaultConfig;
import com.reflexian.levitycosmetics.data.objects.cosmetics.nickname.AssignedNickname;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.AssignedTitle;
import org.bukkit.Bukkit;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.logging.Logger;

public class Database {

    public static final Database shared = new Database();
    private DataSource dataSource;

    private Database() {

    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        }catch (Exception e) {
            initializeDataSource();
            return getConnection();
        }
    }

    final Logger logger = LevityCosmetics.getInstance().getLogger();
    public void initializeDataSource() {
        try {
            final DefaultConfig config = LevityCosmetics.getInstance().getDefaultConfig();

            logger.info("Initializing data source MySQL...");
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setURL("jdbc:mysql://"+config.getDatabaseHost()+":"+config.getDatabasePort()+"/"+config.getDatabaseName());
            mysqlDataSource.setUser(config.getDatabaseUsername());
            mysqlDataSource.setPassword(config.getDatabasePassword());

            dataSource = mysqlDataSource;
            logger.info("Data source initialized successfully!");
            getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS userdata (user_id VARCHAR(36) NOT NULL PRIMARY KEY, trade_banned BOOL, cosmetic_ids TEXT, selected_cosmetic_ids TEXT, extra_pages BIGINT, timestamp BIGINT, PRIMARY KEY (user_id));").execute();
            getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS titles (user_id VARCHAR(36) NOT NULL, cosmeticId VARCHAR(100), titleId TEXT, paintId TEXT, PRIMARY KEY (cosmeticId(100)));").execute();
            getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS nicknames (user_id VARCHAR(36) NOT NULL, cosmeticId VARCHAR(100), content TEXT, paintId TEXT, PRIMARY KEY (cosmeticId(100)));").execute();
            logger.info("Completed data source clean up! Ready to proceed.");
        }catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed to initialize data source. Check the mysql database credentials in the config.yml.");
            logger.severe("Disabling plugin");
            Bukkit.getPluginManager().disablePlugin(LevityCosmetics.getInstance());
        }
    }

    public void save(AssignedNickname nickname) {
        try {
            getConnection().prepareStatement(nickname.toSQL()).execute();
        }catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed to save title for user " + nickname.getUuid().toString() + "!");
        }
    }

    public void save(AssignedTitle title) {
        try {
            getConnection().prepareStatement(title.toSQL()).execute();
        }catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed to save title for user " + title.getUuid().toString() + "!");
        }
    }
}

