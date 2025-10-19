package de.kandiedel.coinSystem;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoinSystem extends JavaPlugin {

    @Override
    public void onEnable() {
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
