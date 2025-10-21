package de.kandiedel.coinSystem;

import de.kandiedel.coinSystem.coins.CoinManager;
import de.kandiedel.coinSystem.commands.CoinCommand;
import de.kandiedel.coinSystem.listener.PlayerJoinListener;
import de.kandiedel.coinSystem.mysql.MySQLManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoinSystem extends JavaPlugin {

    private static final String PREFIX = "§5§lCoinSystem §f§l\uD83E\uDE99 §7";

    private MySQLManager mySQLManager;
    private CoinManager coinManager;

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

        coinManager = new CoinManager(this, mySQLManager);

        getCommand("coins").setExecutor(new CoinCommand(this, coinManager));

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this,  mySQLManager, coinManager), this);

        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "----------------------------------------------");
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + " [ ✔ ] "
                + ChatColor.DARK_PURPLE + ChatColor.BOLD + getDescription().getName()
                + ChatColor.GRAY + ChatColor.BOLD + " successfully enabled!"
                + ChatColor.GREEN + ChatColor.BOLD + " [ ✔ ]");
        getServer().getConsoleSender().sendMessage("        "
                + ChatColor.GRAY + ChatColor.BOLD + "Developed By "
                + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Kandiedel "
                + ChatColor.GRAY + ChatColor.BOLD + "⚒ ");
        getServer().getConsoleSender().sendMessage("");
        if (mySQLManager.isConnected()) {
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + " [ ✔ ] "
                    + ChatColor.DARK_PURPLE + ChatColor.BOLD + "MySQL"
                    + ChatColor.GRAY + ChatColor.BOLD + " connection established!"
                    + ChatColor.GREEN + ChatColor.BOLD + " [ ✔ ]");
            getServer().getConsoleSender().sendMessage("        "
                    + ChatColor.GRAY + ChatColor.BOLD + "Using Database "
                    + ChatColor.DARK_PURPLE + ChatColor.BOLD + getConfig().getString("MySQL.database"));
        } else {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + " [ ✖ ] "
                    + ChatColor.DARK_PURPLE + ChatColor.BOLD + "MySQL"
                    + ChatColor.GRAY + ChatColor.BOLD + " could not be established!"
                    + ChatColor.RED + ChatColor.BOLD + " [ ✖ ]");
            getServer().getConsoleSender().sendMessage("        "
                    + ChatColor.GRAY + ChatColor.BOLD + "Wrong"
                    + ChatColor.DARK_PURPLE + ChatColor.BOLD + " credentials"
                    + ChatColor.GRAY + ChatColor.BOLD + "?");
        }
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "----------------------------------------------");
        getServer().getConsoleSender().sendMessage("");
    }

    @Override
    public void onDisable() {

        mySQLManager.disconnect();

        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "-----------------------------------------------");
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "[ ✖ ] "
                + ChatColor.DARK_PURPLE + ChatColor.BOLD + getDescription().getName()
                + ChatColor.GRAY + ChatColor.BOLD + " successfully disabled!"
                + ChatColor.RED + ChatColor.BOLD + " [ ✖ ]");
        getServer().getConsoleSender().sendMessage("        "
                + ChatColor.GRAY + ChatColor.BOLD + "Developed By "
                + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Kandiedel "
                + ChatColor.GRAY + ChatColor.BOLD + "⚒ ");
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "-----------------------------------------------");
        getServer().getConsoleSender().sendMessage("");
    }

    public static String getPrefix() {
        return PREFIX;
    }
}
