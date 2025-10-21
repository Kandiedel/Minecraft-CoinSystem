package de.kandiedel.coinSystem.coins;

import de.kandiedel.coinSystem.CoinSystem;
import de.kandiedel.coinSystem.mysql.MySQLManager;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CoinManager {

    private CoinSystem coinSystem;
    private final MySQLManager mySQL;

    public CoinManager(CoinSystem coinSystem, MySQLManager mySQL) {
        this.coinSystem = coinSystem;
        this.mySQL = mySQL;
    }

    public void createPlayer(Player player) {
        if (!playerExists(player.getUniqueId())) {
            int startercoins = coinSystem.getConfig().getInt("CoinSystem.start-coins");
            String query = "INSERT INTO player_coins (uuid, username, coins) VALUES ('"
                    + player.getUniqueId() + "', '" + player.getName() + "', '" + startercoins + "')";
            mySQL.executeUpdate(query);
        }
    }

    public boolean playerExists(UUID uuid) {
        try (ResultSet rs = mySQL.executeQuery("SELECT uuid FROM player_coins WHERE uuid = '" + uuid + "'")) {
            return rs != null && rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public long getCoins(UUID uuid) {
        try (ResultSet rs = mySQL.executeQuery("SELECT coins FROM player_coins WHERE uuid = '" + uuid + "'")) {
            if (rs != null && rs.next()) {
                return rs.getLong("coins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setCoins(UUID uuid, long amount) {
        mySQL.executeUpdate("UPDATE player_coins SET coins = " + amount + " WHERE uuid = '" + uuid + "'");
    }

    public void addCoins(UUID uuid, long amount) {
        long coins = getCoins(uuid);
        setCoins(uuid, coins + amount);
    }

    public void removeCoins(UUID uuid, long amount) {
        long coins = getCoins(uuid);
        long newAmount = Math.max(0, coins - amount);
        setCoins(uuid, newAmount);
    }

    public boolean payCoins(UUID from, UUID to, long amount) {
        long senderCoins = getCoins(from);
        if (senderCoins < amount) return false;

        removeCoins(from, amount);
        addCoins(to, amount);
        return true;
    }

    public Map<String, Long> getTopPlayers(int limit) {
        Map<String, Long> top = new LinkedHashMap<>();
        try (ResultSet rs = mySQL.executeQuery("SELECT username, coins FROM player_coins ORDER BY coins DESC LIMIT " + limit)) {
            while (rs != null && rs.next()) {
                top.put(rs.getString("username"), rs.getLong("coins"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return top;
    }
}