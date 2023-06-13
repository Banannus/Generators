package dk.banannus.generators.commands.subcommands.slots;

import dk.banannus.generators.commands.SubCommand;
import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.player.SlotsManager;
import dk.banannus.generators.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static dk.banannus.generators.utils.isInt.isInt;

public class GenAddSlotsCommand extends SubCommand {
	@Override
	public String getName() {
		return "slot";
	}

	@Override
	public String getSyntax() {
		return "&e/gen slot add <player> <antal>";
	}

	@Override
	public String getDescription() {
		return "&7Tilføj slots til en spiller";
	}

	@Override
	public void perform(Player player, String[] args) {
		if (args[0].equalsIgnoreCase("slots") && args.length == 4 && args[1].equalsIgnoreCase("add") && isInt(args[3])) {
			if (!player.hasPermission(ConfigManager.get("admin.staff-permission")[0])) {
				ConfigManager.send(player, "admin.staff-deny-message");
				return;
			}
			Player target = Bukkit.getPlayer(args[2]);
			if (target != null) {
				SlotsManager.addSlots(target.getUniqueId(), Double.parseDouble(args[3]));
				ConfigManager.send(player, "messages.add-slots-to-player", "%player%", target.getName(), "%slots%", args[3]);
			} else {
				ConfigManager.send(player, "messages.player-does-not-exist");
			}
		} else if (args.length < 4){
			player.sendMessage(ConfigManager.get("messages.prefix")[0] + " " + Chat.colored("&e/gen slot <remove/add/set> <player> <antal>"));
		}
	}
}