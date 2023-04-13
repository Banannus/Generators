package dk.banannus.generators.commands;

import dk.banannus.generators.commands.subcommands.GenAddMultiplierCommand;
import dk.banannus.generators.commands.subcommands.GenListCommand;
import dk.banannus.generators.commands.subcommands.GenRemoveMultiplierCommand;
import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.utils.Chat;
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
			for(int i = 0; i < getSubCommands().size(); i++) {
				player.sendMessage(Chat.colored("&8┃ "+ getSubCommands().get(i).getSyntax() + " &8- " + getSubCommands().get(i).getDescription()));
			}
		}
		return true;
	}

	public ArrayList<SubCommand> getSubCommands() {
		return subCommands;
	}
}
