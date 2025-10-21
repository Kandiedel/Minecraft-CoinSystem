package de.kandiedel.coinSystem.listener;

import de.kandiedel.coinSystem.CoinSystem;
import de.kandiedel.coinSystem.coins.CoinManager;
import de.kandiedel.coinSystem.mysql.MySQLManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private CoinSystem coinSystem;
    private MySQLManager mySQLManager;
    private CoinManager coinManager;

    public PlayerJoinListener(CoinSystem coinSystem,  MySQLManager mySQLManager, CoinManager coinManager) {
        this.coinSystem = coinSystem;
        this.mySQLManager = mySQLManager;
        this.coinManager = coinManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        coinSystem.reloadConfig();

        Player player = event.getPlayer();

        if (!mySQLManager.isConnected()) {
            return;
        }

        if (!coinManager.playerExists(player.getUniqueId())) {
            coinManager.createPlayer(player);
        }
    }

}
