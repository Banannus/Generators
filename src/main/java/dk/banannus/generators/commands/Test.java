package dk.banannus.generators.commands;

import dk.banannus.generators.data.gen.Gen;
import dk.banannus.generators.Generators;
import dk.banannus.generators.data.gen.GensManager;
import dk.banannus.generators.data.player.PlayerData;
import dk.banannus.generators.data.player.PlayerDataManager;
import dk.banannus.generators.data.player.slots.SlotsManager;
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
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {


		HashMap<String, Gen> genValues = GensManager.getGenList();

		Gen test = genValues.get("2");

		Player p = (Player) commandSender;
		UUID uuid = p.getUniqueId();

		if(args[0].equalsIgnoreCase("save")) {
			PlayerDataManager playerDataManager = new PlayerDataManager();
			playerDataManager.saveAll(uuid);
		}

		HashMap<UUID, Set<PlayerData>> online = PlayerDataManager.getOnlinePlayerDataList();
		HashMap<UUID, Set<PlayerData>> offline = PlayerDataManager.getOfflinePlayerDataList();
		HashMap<UUID, Set<PlayerData>> all = PlayerDataManager.getAllPlayerDataList();

		if(args[0].equalsIgnoreCase("online")) {
			Bukkit.broadcastMessage(String.valueOf(GensManager.getGenBlockList()));
		}

		if(args[0].equalsIgnoreCase("offline")) {
			Bukkit.broadcastMessage(String.valueOf(offline));
		}

		if(args[0].equalsIgnoreCase("all")) {
			Bukkit.broadcastMessage(String.valueOf(all));
		}

		if(args[0].equalsIgnoreCase("slots")) {
			Bukkit.broadcastMessage(String.valueOf(SlotsManager.getSlots(uuid)));
			Bukkit.broadcastMessage(String.valueOf(SlotsManager.getSlotsList()));
			if(args[1].equalsIgnoreCase("add")) {
				SlotsManager.addSlots(uuid, 10);
			}
		}
		return false;
	}
}
