package com.reflexian.levitycosmetics.data;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.reflexian.levitycosmetics.LevityCosmetics;
import com.reflexian.levitycosmetics.data.configs.DefaultConfig;
import org.bukkit.Bukkit;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {

    public static final Database shared = new Database();
    private DataSource dataSource;

    private Database() {

    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void initializeDataSource() {
        var logger = LevityCosmetics.getInstance().getLogger();
        try {
            final DefaultConfig config = LevityCosmetics.getInstance().getDefaultConfig();

            logger.info("Initializing data source MySQL...");
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setURL("jdbc:mysql://"+config.getDatabaseHost()+":"+config.getDatabasePort()+"/"+config.getDatabaseName());
            mysqlDataSource.setUser(config.getDatabaseUsername());
            mysqlDataSource.setPassword(config.getDatabasePassword());

            dataSource = mysqlDataSource;
            logger.info("Data source initialized successfully!");
            getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS userdata (user_id VARCHAR(36) NOT NULL, trade_banned BOOL, cosmetic_ids TEXT, timestamp BIGINT, PRIMARY KEY (user_id));").execute();
            logger.info("Completed data source clean up! Ready to proceed.");
        }catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed to initialize data source. Check the mysql database credentials in the config.yml.");
            logger.severe("Disabling plugin");
            Bukkit.getPluginManager().disablePlugin(LevityCosmetics.getInstance());
        }
    }
}

