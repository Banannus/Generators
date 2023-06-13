package dk.banannus.generators.commands.subcommands;

import dk.banannus.generators.commands.SubCommand;
import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.player.PlayerDataManager;
import dk.banannus.generators.data.player.SlotsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GenAdminCommand extends SubCommand {

	@Override
	public String getName() {
		return "admin";
	}

	@Override
	public String getSyntax() {
		return "&e/gen admin <player>";
	}

	@Override
	public String getDescription() {
		return "&7Admin menu til staffs";
	}

	@Override
	public void perform(Player player, String[] args) {
		if (args[0].equalsIgnoreCase("admin") && args.length == 2) {
			if (!player.hasPermission(ConfigManager.get("admin.staff-permission")[0])) {
				ConfigManager.send(player, "admin.staff-deny-message");
				return;
			}
			Player target = Bukkit.getPlayer(args[2]);
			if (target != null) {
				opretGUI(player);
			} else {
				ConfigManager.send(player, "messages.player-does-not-exist");
			}
		}
	}

	public void opretGUI(Player player) {

	}
}
