package dk.banannus.generators.commands;

import dk.banannus.generators.GenData.GensManager;
import dk.banannus.generators.GenData.GetGenValue;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static dk.banannus.generators.GenData.GensManager.genValues;

public class Test implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

		GetGenValue test = genValues.get("2");

		String block = test.getBlock();
		int price = test.getSalgspris();

		Bukkit.broadcastMessage(block + price);

		return false;
	}
}
