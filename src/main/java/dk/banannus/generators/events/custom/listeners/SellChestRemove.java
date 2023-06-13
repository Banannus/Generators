package dk.banannus.generators.events.custom.listeners;

import dk.banannus.generators.commands.subcommands.GenListCommand;
import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.sellchest.SellChestManager;
import dk.banannus.generators.events.custom.events.SellChestRemoveEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import static dk.banannus.generators.commands.subcommands.GenListCommand.setDisplayName;

public class SellChestRemove implements Listener {

	@EventHandler
	public void onSellChestBreak(SellChestRemoveEvent e) {

		if(e.isCancelled()) {
			e.setCancelled(true);
			return;
		}

		if(!(e.getAction() == Action.LEFT_CLICK_BLOCK)) return;

		if(!SellChestManager.getAllLocations().contains(e.getClickedBlock().getLocation())) return;

		if(e.getClickedBlock().getLocation().equals(SellChestManager.getSellChestPlayerLocationList().get(e.getPlayer().getUniqueId()))) {
			SellChestManager.getSellChestPlayerLocationList().remove(e.getPlayer().getUniqueId());
			ConfigManager.send(e.getPlayer(), "messages.remove-sell-chest");
			ItemStack itemStack = setDisplayName(new ItemStack(Material.ENDER_CHEST), ConfigManager.get("sell-chest.name")[0]);
			e.getPlayer().getInventory().addItem(itemStack);
			e.getClickedBlock().setType(Material.AIR);
		} else {
			ConfigManager.send(e.getPlayer(), "messages.sell-chest-not-yours");
		}
	}
}
