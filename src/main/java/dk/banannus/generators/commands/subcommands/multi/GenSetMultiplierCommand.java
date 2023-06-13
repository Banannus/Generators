package dk.banannus.generators.commands.subcommands.multi;

import dk.banannus.generators.commands.SubCommand;
import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.player.MultiplierManager;
import dk.banannus.generators.data.player.SlotsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static dk.banannus.generators.utils.isInt.isInt;

public class GenSetMultiplierCommand extends SubCommand {
	@Override
	public String getName() {
		return "multiplier";
	}

	@Override
	public String getSyntax() {
		return "&e/gen multiplier set <player> <antal>";
	}

	@Override
	public String getDescription() {
		return "&7Sæt en spillers multiplier";
	}

	@Override
	public void perform(Player player, String[] args) {
		if (args[0].equalsIgnoreCase("multiplier") && args.length == 4 && args[1].equalsIgnoreCase("set") && isInt(args[3])) {
			if (!player.hasPermission(ConfigManager.get("admin.staff-permission")[0])) {
				ConfigManager.send(player, "admin.staff-deny-message");
				return;
			}
			Player target = Bukkit.getPlayer(args[2]);
			if (target != null) {
				MultiplierManager.setPlayerMultiplier(target.getUniqueId(), Double.parseDouble(args[3]));
				ConfigManager.send(player, "messages.set-multiplier-of-player", "%player%", target.getName(), "%multi%", args[3]);
			} else {
				ConfigManager.send(player, "messages.player-does-not-exist");
			}
		}
	}
}