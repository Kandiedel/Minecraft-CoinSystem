package de.kandiedel.coinSystem;

import de.kandiedel.coinSystem.mysql.MySQLManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoinSystem extends JavaPlugin {

    private MySQLManager mySQLManager;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        mySQLManager = new MySQLManager(
                getConfig().getString("MySQL.host"),
                getConfig().getInt("MySQL.port"),
                getConfig().getString("MySQL.database"),
                getConfig().getString("MySQL.username"),
                getConfig().getString("MySQL.password"));
        mySQLManager.connect();
        if (mySQLManager.isConnected()) {
            mySQLManager.createTable("player_coins",
                    "uuid CHAR(36) NOT NULL PRIMARY KEY," +
                            "username VARCHAR(32) NOT NULL," +
                            "coins BIGINT NOT NULL DEFAULT 0," +
                            "last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP");
        }

        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "============================================");
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[ ✔ ] "
                + ChatColor.DARK_PURPLE + getDescription().getName()
                + ChatColor.GRAY + " successfully enabled!"
                + ChatColor.GREEN + " [ ✔ ]");
        getServer().getConsoleSender().sendMessage("        "
                + ChatColor.GRAY + "Developed By "
                + ChatColor.DARK_PURPLE + "Kandiedel " + ChatColor.GRAY + "⚒ ");
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "============================================");
        getServer().getConsoleSender().sendMessage("");
    }

    @Override
    public void onDisable() {

        mySQLManager.disconnect();

        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "=============================================");
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[ ✖ ] "
                + ChatColor.DARK_PURPLE + getDescription().getName()
                + ChatColor.GRAY + " successfully disabled!"
                + ChatColor.RED + " [ ✖ ]");
        getServer().getConsoleSender().sendMessage("        "
                + ChatColor.GRAY + "Developed By "
                + ChatColor.DARK_PURPLE + "Kandiedel " + ChatColor.GRAY + "⚒ ");
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "=============================================");
        getServer().getConsoleSender().sendMessage("");
    }
}
