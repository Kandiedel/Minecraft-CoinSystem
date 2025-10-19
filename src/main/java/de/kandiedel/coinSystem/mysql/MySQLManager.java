package de.kandiedel.coinSystem.mysql;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.*;

public class MySQLManager {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private Connection connection;

    public MySQLManager(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    // Get Connection
    public void connect() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password
                );
                Bukkit.getServer().getConsoleSender().sendMessage("");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "==============================================");
                Bukkit.getServer().getConsoleSender().sendMessage("");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[ MySQL ] "
                        + ChatColor.GRAY + "Connection successfully established!");
                Bukkit.getServer().getConsoleSender().sendMessage("");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "==============================================");
                Bukkit.getServer().getConsoleSender().sendMessage("");
            } catch (SQLException e) {
                Bukkit.getServer().getConsoleSender().sendMessage("");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "=====================================");
                Bukkit.getServer().getConsoleSender().sendMessage("");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[ MySQL ] "
                        + ChatColor.GRAY + "Connection not established!");
                Bukkit.getServer().getConsoleSender().sendMessage("            "
                        + ChatColor.GRAY + "Wrong Credentials? ");
                Bukkit.getServer().getConsoleSender().sendMessage("");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "=====================================");
                Bukkit.getServer().getConsoleSender().sendMessage("");
            }
        }
    }

    // Check connection
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Close connection
    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
                Bukkit.getServer().getConsoleSender().sendMessage("");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "=========================================");
                Bukkit.getServer().getConsoleSender().sendMessage("");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[ MySQL ] "
                        + ChatColor.GRAY + "Connection successfully closed!");
                Bukkit.getServer().getConsoleSender().sendMessage("");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "=========================================");
                Bukkit.getServer().getConsoleSender().sendMessage("");
            } catch (SQLException e) {
                Bukkit.getServer().getConsoleSender().sendMessage("");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "=========================================");
                Bukkit.getServer().getConsoleSender().sendMessage("");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[ MySQL ] "
                        + ChatColor.GRAY + "Connection could not be closed!");
                Bukkit.getServer().getConsoleSender().sendMessage("");
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "=========================================");
                Bukkit.getServer().getConsoleSender().sendMessage("");
            }
        }
    }

    // Create table
    public void createTable(String tableName, String columns) {
        if (isConnected()) {
            try (Statement statement = connection.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columns + ")";
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Insert data / update / delete
    public void executeUpdate(String query) {
        if (isConnected()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Read data
    public ResultSet executeQuery(String query) {
        if (isConnected()) {
            try {
                Statement statement = connection.createStatement();
                return statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Example: Read all data from table
    public void printAll(String tableName) {
        ResultSet rs = executeQuery("SELECT * FROM " + tableName);
        if (rs != null) {
            try {
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();

                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(rs.getString(i) + "\t");
                    }
                    System.out.println();
                }

                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}