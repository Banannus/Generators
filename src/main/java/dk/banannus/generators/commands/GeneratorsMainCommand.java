package dk.banannus.generators.commands;

import dk.banannus.generators.commands.subcommands.*;
import dk.banannus.generators.commands.subcommands.multi.GenAddMultiplierCommand;
import dk.banannus.generators.commands.subcommands.multi.GenRemoveMultiplierCommand;
import dk.banannus.generators.commands.subcommands.multi.GenSetMultiplierCommand;
import dk.banannus.generators.commands.subcommands.slots.GenAddSlotsCommand;
import dk.banannus.generators.commands.subcommands.slots.GenRemoveSlotsCommand;
import dk.banannus.generators.commands.subcommands.slots.GenSetSlotsCommand;
import dk.banannus.generators.commands.subcommands.xp.GenAddXPCommand;
import dk.banannus.generators.commands.subcommands.xp.GenRemoveXPCommand;
import dk.banannus.generators.commands.subcommands.xp.GenSetXPCommand;
import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.utils.Chat;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GeneratorsMainCommand implements CommandExecutor {

	private ArrayList<SubCommand> subCommands = new ArrayList<>();

	public GeneratorsMainCommand() {
		subCommands.add(new GenListCommand());
		subCommands.add(new GenAddMultiplierCommand());
		subCommands.add(new GenRemoveMultiplierCommand());
		subCommands.add((new GenSetMultiplierCommand()));
		subCommands.add((new GenAddSlotsCommand()));
		subCommands.add((new GenRemoveSlotsCommand()));
		subCommands.add((new GenSetSlotsCommand()));
		subCommands.add(new GenAddXPCommand());
		subCommands.add(new GenRemoveXPCommand());
		subCommands.add(new GenSetXPCommand());
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Chat.colored("&cThis command can only be run by a player."));
			return true;
		}



		Player player = (Player) sender;

		if (args.length > 0) {
			for(int i = 0; i < getSubCommands().size(); i++) {
				if(args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
					getSubCommands().get(i).perform(player, args);
				}
			}
		} else {
			player.sendMessage(ConfigManager.get("messages.prefix"));
			player.sendMessage("");
			if(!player.hasPermission(ConfigManager.get("admin.staff-permission")[0])) {
				player.sendMessage("&8┃ "+ getSubCommands().get(0).getSyntax() + " &8- " + getSubCommands().get(0).getDescription());
				return false;
			} else {
				player.sendMessage("&8┃ "+ getSubCommands().get(0).getSyntax() + " &8- " + getSubCommands().get(0).getDescription());
				player.sendMessage(Chat.colored("&8┃ &e/gen slots <add/remove/set> <player> <antal>" + " &8- &7Slots command"));
				player.sendMessage(Chat.colored("&8┃ &e/gen multiplier <add/remove/set> <player> <antal>" + " &8- &7Multiplier command"));
				player.sendMessage(Chat.colored("&8┃ &e/gen xp <add/remove/set> <player> <antal>" + " &8- &7Xp command"));
			}
		}
		return true;
	}

	public ArrayList<SubCommand> getSubCommands() {
		return subCommands;
	}
}
