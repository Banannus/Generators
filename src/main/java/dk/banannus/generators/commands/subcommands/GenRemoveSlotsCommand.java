package dk.banannus.generators.commands.subcommands;

import dk.banannus.generators.commands.SubCommand;
import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.player.MultiplierManager;
import dk.banannus.generators.data.player.SlotsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static dk.banannus.generators.utils.isInt.isInt;

public class GenRemoveSlotsCommand extends SubCommand {
    @Override
    public String getName() {
        return "slot";
    }

    @Override
    public String getSyntax() {
        return "&e/gen slot remove <player> <antal>";
    }

    @Override
    public String getDescription() {
        return "Fjern slots fra en spiller";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args[0].equalsIgnoreCase("slot") && args.length == 4 && args[1].equalsIgnoreCase("remove") && isInt(args[3])) {
            if (!player.hasPermission(ConfigManager.get("admin.staff-permission")[0])) {
                ConfigManager.send(player, "admin.staff-deny-message");
                return;
            }
            Player target = Bukkit.getPlayer(args[2]);
            if (target != null) {
                SlotsManager.removeSlots(target.getUniqueId(), Double.parseDouble(args[2]));
                ConfigManager.send(player, "messages.add-multi-to-player", "%player%", target.getName(), "%multi%", args[3], "%totalmulti%", String.valueOf(MultiplierManager.getPlayerMultiplier(target.getUniqueId())));
            } else {
                ConfigManager.send(player, "messages.player-does-not-exist");
            }
        }
    }
}
