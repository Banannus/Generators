package dk.banannus.generators.commands.subcommands.xp;

import dk.banannus.generators.commands.SubCommand;
import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.player.XpManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static dk.banannus.generators.utils.isInt.isInt;

public class GenRemoveXPCommand extends SubCommand {

	@Override
	public String getName() {
		return "xp";
	}

	@Override
	public String getSyntax() {
		return "&e/gen xp remove <player> <antal>";
	}

	@Override
	public String getDescription() {
		return "&7Fjern xp fra en spiller";
	}

	@Override
	public void perform(Player player, String[] args) {
		if (args[0].equalsIgnoreCase("xp") && args.length == 4 && args[1].equalsIgnoreCase("remove") && isInt(args[3])) {
			if (!player.hasPermission(ConfigManager.get("admin.staff-permission")[0])) {
				ConfigManager.send(player, "admin.staff-deny-message");
				return;
			}
			Player target = Bukkit.getPlayer(args[2]);
			if (target != null) {
				XpManager.removeXP(target.getUniqueId(), Double.parseDouble(args[3]));
				ConfigManager.send(player, "messages.remove-xp-from-player", "%player%", target.getName(), "%xp%", args[3]);
			} else {
				ConfigManager.send(player, "messages.player-does-not-exist");
			}
		}
	}
}
