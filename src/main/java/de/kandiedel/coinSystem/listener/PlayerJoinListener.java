package de.kandiedel.coinSystem.listener;

import de.kandiedel.coinSystem.CoinSystem;
import de.kandiedel.coinSystem.mysql.MySQLManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerJoinListener implements Listener {

    private CoinSystem coinSystem;
    private MySQLManager mySQLManager;

    public PlayerJoinListener(CoinSystem coinSystem,  MySQLManager mySQLManager) {
        this.coinSystem = coinSystem;
        this.mySQLManager = mySQLManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        coinSystem.reloadConfig();

        Player player = event.getPlayer();

        if (!mySQLManager.isConnected()) {
            return;
        }

        try {
            ResultSet rs = mySQLManager.executeQuery(
                    "SELECT * FROM player_coins WHERE uuid = '" + player.getUniqueId() + "'");

            if (rs != null && rs.next()) {
                return;
            } else {
                int startCoins = coinSystem.getConfig().getInt("CoinSystem.start-coins");

                mySQLManager.executeUpdate("INSERT INTO player_coins (uuid, username, coins) VALUES ('"
                        + player.getUniqueId() + "', '"
                        + player.getName() + "', " + startCoins + ")");
            }

            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
