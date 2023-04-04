package dk.banannus.generators.commands;

import dk.banannus.generators.Data.Gen;
import dk.banannus.generators.Data.PlayerData;
import dk.banannus.generators.Generators;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

import static dk.banannus.generators.Data.GensManager.genValues;
import static dk.banannus.generators.Data.PlayerData.playerDataValues;
import static dk.banannus.generators.Data.PlayerData.saveAll;

public class Test implements CommandExecutor {

	private final Generators plugin = Generators.instance;

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

		Gen test = genValues.get("2");

		Player p = (Player) commandSender;
		UUID uuid = p.getUniqueId();

		if(args[0].equalsIgnoreCase("save")) {
			saveAll(uuid);
		}

		if(args[0].equalsIgnoreCase("test")) {
			Bukkit.broadcastMessage(String.valueOf(playerDataValues));
		}

		return false;
	}
}
