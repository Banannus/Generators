package dk.banannus.generators.commands;

import dk.banannus.generators.data.gen.Gen;
import dk.banannus.generators.Generators;
import dk.banannus.generators.data.gen.GensManager;
import dk.banannus.generators.data.player.PlayerData;
import dk.banannus.generators.data.player.PlayerDataManager;
import dk.banannus.generators.data.sellchest.SellChestItem;
import dk.banannus.generators.data.sellchest.SellChestManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;


public class Test implements CommandExecutor {

	private final Generators plugin = Generators.instance;

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {


		HashMap<String, Gen> genValues = GensManager.getGenList();

		Gen test = genValues.get("2");

		Player p = (Player) sender;
		UUID uuid = p.getUniqueId();

		if (args[0].equalsIgnoreCase("save")) {
			PlayerDataManager playerDataManager = new PlayerDataManager();
			playerDataManager.saveAll(uuid);
		}

		HashMap<UUID, Set<PlayerData>> online = PlayerDataManager.getOnlinePlayerDataList();
		HashMap<UUID, Set<PlayerData>> offline = PlayerDataManager.getOfflinePlayerDataList();
		HashMap<UUID, Set<PlayerData>> all = PlayerDataManager.getAllPlayerDataList();

		if (args[0].equalsIgnoreCase("online")) {
			Bukkit.broadcastMessage(SellChestManager.getSellChestItems().get(uuid).toString());
		}

		if (args[0].equalsIgnoreCase("offline")) {
			Bukkit.broadcastMessage(String.valueOf(offline));
		}

		if (args[0].equalsIgnoreCase("all")) {
			Bukkit.broadcastMessage(String.valueOf(all));
		}

		if (args.length < 2) {
			sender.sendMessage("Usage: /gen item <start|stop> <player>");
			return true;
		}

		if (args[0].equalsIgnoreCase("item")) {
			SellChestManager sellChestManager = new SellChestManager();

			if (args[1].equalsIgnoreCase("stop")) {
				sellChestManager.cancelGenDrops(uuid);
				sender.sendMessage("Stopped generating items for player " + uuid);
			} else if (args[1].equalsIgnoreCase("start")) {
				sellChestManager.startGenDrops(uuid);
				sender.sendMessage("Started generating items for player " + uuid);
			} else if (args[1].equalsIgnoreCase("list")) {
				sender.sendMessage("SellChestLoc: " + SellChestManager.getSellChestItems().get(uuid));
			} else if (args[1].equalsIgnoreCase("save")) {
				Bukkit.broadcastMessage("Saved");
			}
		} else {
			sender.sendMessage("Invalid argument '" + args[1] + "'. Usage: /gen item <start|stop> <player>");
		}

		return true;

	}
}
