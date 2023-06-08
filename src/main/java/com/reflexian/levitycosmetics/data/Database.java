package com.reflexian.levitycosmetics.data;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.reflexian.levitycosmetics.LevityCosmetics;
import org.bukkit.Bukkit;

public class Database {
    private static final String DB_URL = "jdbc:mysql://lewan.bloom.host:3306/s36650_Test";
    private static final String DB_USER = "u36650_vflgkd8VCo";
    private static final String DB_PASSWORD = "rNGjWmD0BDZlnnYI0r0yw4YQ";

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
            logger.info("Initializing data source MySQL...");
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setURL(DB_URL);
            mysqlDataSource.setUser(DB_USER);
            mysqlDataSource.setPassword(DB_PASSWORD);

            dataSource = mysqlDataSource;
            logger.info("Data source initialized successfully!");
            getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS userdata (user_id VARCHAR(36) NOT NULL, cosmetic_ids TEXT, timestamp BIGINT, PRIMARY KEY (user_id));").execute();
            logger.info("Completed data source clean up! Ready to proceed.");
        }catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed to initialize data source");
            logger.severe("Disabling plugin");
            Bukkit.getPluginManager().disablePlugin(LevityCosmetics.getInstance());
        }
    }
}

