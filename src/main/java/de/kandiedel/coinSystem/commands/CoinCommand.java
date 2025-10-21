package de.kandiedel.coinSystem.commands;

import de.kandiedel.coinSystem.CoinSystem;
import de.kandiedel.coinSystem.coins.CoinManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CoinCommand implements CommandExecutor {

    private final CoinSystem coinSystem;
    private final CoinManager coinsManager;

    public CoinCommand(CoinSystem coinSystem, CoinManager coinsManager) {
        this.coinSystem = coinSystem;
        this.coinsManager = coinsManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(" ");
            sender.sendMessage(coinSystem.getPrefix() + "§cNur Spieler können diesen Command benutzen§7!");
            sender.sendMessage(" ");
            return true;
        }

        coinsManager.createPlayer(player);

        if (args.length == 0) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§7Du hast §d§l" + coinsManager.getCoins(player.getUniqueId()) + " §7Coins.");
            player.sendMessage(" ");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "balance" -> handleBalance(player, args);
            case "add" -> handleAdd(player, args);
            case "remove" -> handleRemove(player, args);
            case "set" -> handleSet(player, args);
            case "pay" -> handlePay(player, args);
            case "top" -> handleTop(player);
            default -> sendHelp(player);
        }

        return true;
    }

    private void handleBalance(Player player, String[] args) {
        if (!player.hasPermission("coins.cmd.balance")) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§cKeine Rechte§7!");
            player.sendMessage(" ");
            return;
        }

        if (args.length == 1) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§7Du hast §d§l" + coinsManager.getCoins(player.getUniqueId()) + " §7Coins.");
            player.sendMessage(" ");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§cSpieler nicht gefunden§7!");
            player.sendMessage(" ");
            return;
        }

        player.sendMessage(" ");
        player.sendMessage(coinSystem.getPrefix() + "§7" + target.getName() + " hat §d§l" + coinsManager.getCoins(target.getUniqueId()) + " §7Coins.");
        player.sendMessage(" ");
    }

    private void handleAdd(Player player, String[] args) {
        if (!player.hasPermission("coins.cmd.add")) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§cKeine Rechte§7!");
            player.sendMessage(" ");
            return;
        }

        if (args.length < 3) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§7Benutzung: /coins add <Spieler> <Betrag>");
            player.sendMessage(" ");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§cSpieler nicht gefunden§7!");
            player.sendMessage(" ");
            return;
        }

        long amount = Long.parseLong(args[2]);
        coinsManager.addCoins(target.getUniqueId(), amount);

        player.sendMessage(" ");
        player.sendMessage(coinSystem.getPrefix() + "§7Du hast §d" + amount + " §7Coins an §d" + target.getName() + " §7gegeben.");
        player.sendMessage(" ");
    }

    private void handleRemove(Player player, String[] args) {
        if (!player.hasPermission("coins.cmd.remove")) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§cKeine Rechte§7!");
            player.sendMessage(" ");
            return;
        }

        if (args.length < 3) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§7Benutzung: /coins remove <Spieler> <Betrag>");
            player.sendMessage(" ");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§cSpieler nicht gefunden§7!");
            player.sendMessage(" ");
            return;
        }

        long amount = Long.parseLong(args[2]);
        coinsManager.removeCoins(target.getUniqueId(), amount);

        player.sendMessage(" ");
        player.sendMessage(coinSystem.getPrefix() + "§7Du hast §d" + amount + " §7Coins von §d" + target.getName() + " §7entfernt.");
        player.sendMessage(" ");
    }

    private void handleSet(Player player, String[] args) {
        if (!player.hasPermission("coins.cmd.set")) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§cKeine Rechte§7!");
            player.sendMessage(" ");
            return;
        }

        if (args.length < 3) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§7Benutzung: /coins set <Spieler> <Betrag>");
            player.sendMessage(" ");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§cSpieler nicht gefunden§7!");
            player.sendMessage(" ");
            return;
        }

        long amount = Long.parseLong(args[2]);
        coinsManager.setCoins(target.getUniqueId(), amount);

        player.sendMessage(" ");
        player.sendMessage(coinSystem.getPrefix() + "§7Coins von §d" + target.getName() + " §7wurden auf §d" + amount + " §7gesetzt.");
        player.sendMessage(" ");
    }

    private void handlePay(Player player, String[] args) {
        if (!player.hasPermission("coins.cmd.pay")) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§cKeine Rechte§7!");
            player.sendMessage(" ");
            return;
        }

        if (args.length < 3) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§7Benutzung: /coins pay <Spieler> <Betrag>");
            player.sendMessage(" ");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§cSpieler nicht gefunden§7!");
            player.sendMessage(" ");
            return;
        }

        if (target.equals(player)) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§cDu kannst dir selbst keine Coins senden§7!");
            player.sendMessage(" ");
            return;
        }

        long amount = Long.parseLong(args[2]);
        if (coinsManager.payCoins(player.getUniqueId(), target.getUniqueId(), amount)) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§7Du hast §d" + amount + " §7Coins an §d" + target.getName() + " §7gesendet.");
            target.sendMessage(" ");
            target.sendMessage(coinSystem.getPrefix() + "§7Du hast §d" + amount + " §7Coins von §d" + player.getName() + " §7erhalten.");
            target.sendMessage(" ");
            player.sendMessage(" ");
        } else {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§cDu hast nicht genug Coins§7!");
            player.sendMessage(" ");
        }
    }

    private void handleTop(Player player) {
        if (!player.hasPermission("coins.cmd.top")) {
            player.sendMessage(" ");
            player.sendMessage(coinSystem.getPrefix() + "§cKeine Rechte§7!");
            player.sendMessage(" ");
            return;
        }

        player.sendMessage(" ");
        player.sendMessage("§8§m-----§r §d§lTop 10 Coins §8§m-----");
        int rank = 1;
        for (var entry : coinsManager.getTopPlayers(10).entrySet()) {
            player.sendMessage("§7#" + rank + " §d" + entry.getKey() + " §8» §f" + entry.getValue());
            rank++;
        }
        player.sendMessage("§8§m---------------------------");
        player.sendMessage(" ");
    }

    private void sendHelp (Player player) {
        player.sendMessage(" ");
        player.sendMessage(coinSystem.getPrefix() + "§8§m----------------------------------");
        player.sendMessage(coinSystem.getPrefix() + " §5§lBenutzung vom Coins-Command:");
        player.sendMessage(coinSystem.getPrefix() + "   /coins");
        player.sendMessage(coinSystem.getPrefix() + "   /coins balance <Spieler>");
        player.sendMessage(coinSystem.getPrefix() + "   /coins add <Spieler>");
        player.sendMessage(coinSystem.getPrefix() + "   /coins remove");
        player.sendMessage(coinSystem.getPrefix() + "   /coins set <Spieler> <Betrag>");
        player.sendMessage(coinSystem.getPrefix() + "   /coins pay <Spieler> <Betrag>");
        player.sendMessage(coinSystem.getPrefix() + "   /coins top");
        player.sendMessage(coinSystem.getPrefix() + "§8§m----------------------------------");
        player.sendMessage(" ");
    }
}
