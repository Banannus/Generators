package dk.banannus.generators.commands;

import dk.banannus.generators.data.gen.Gen;
import dk.banannus.generators.Generators;
import dk.banannus.generators.data.player.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static dk.banannus.generators.data.gen.GensManager.genValues;
import static dk.banannus.generators.data.player.PlayerData.playerDataValues;

public class Test implements CommandExecutor {

	private final Generators plugin = Generators.instance;

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

		Gen test = genValues.get("2");

		Player p = (Player) commandSender;
		UUID uuid = p.getUniqueId();

		if(args[0].equalsIgnoreCase("save")) {
			PlayerDataManager playerDataManager = new PlayerDataManager();
			playerDataManager.saveAll(uuid);
		}

		if(args[0].equalsIgnoreCase("test")) {
			Bukkit.broadcastMessage(String.valueOf(playerDataValues));
		}

		return false;
	}
}
