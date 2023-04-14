package dk.banannus.generators.commands.subcommands;

import dk.banannus.generators.commands.SubCommand;
import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.player.MultiplierManager;
import dk.banannus.generators.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GenAddMultiplierCommand extends SubCommand {

	@Override
	public String getName() {
		return "multiplier";
	}

	@Override
	public String getSyntax() {
		return "&e/gen multiplier add <player> <antal>";
	}

	@Override
	public String getDescription() {
		return "&7Add multiplier til en spiller.";
	}

	@Override
	public void perform(Player player, String[] args) {
		if (args[0].equalsIgnoreCase("multiplier") && args.length == 4 && args[1].equalsIgnoreCase("add")) {
			if (!player.hasPermission(ConfigManager.get("admin.staff-permission")[0])) {
				ConfigManager.send(player, "admin.staff-deny-message");
				return;
			}
			Player target = Bukkit.getPlayer(args[2]);
			if (target != null) {
				MultiplierManager.addPlayerMultiplier(target.getUniqueId(), Double.parseDouble(args[3]));
				ConfigManager.send(player, "messages.add-multi-to-player", "%player%", target.getName(), "%multi%", args[3], "%totalmulti%", String.valueOf(MultiplierManager.getPlayerMultiplier(target.getUniqueId())));
			} else {
				ConfigManager.send(player, "messages.player-does-not-exist");
			}
		}                      
	}
}
