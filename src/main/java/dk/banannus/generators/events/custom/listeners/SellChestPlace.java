package dk.banannus.generators.events.custom.listeners;

import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.sellchest.SellChestManager;
import dk.banannus.generators.events.custom.events.SellChestPlaceEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SellChestPlace implements Listener {

	@EventHandler
	public void onSellChestPlace(SellChestPlaceEvent e) {

		if(!ConfigManager.get("sell-chest.name")[0].equals(e.getPlayer().getItemInHand().getItemMeta().getDisplayName())) {
			return;
		}

		SellChestManager sellChestManager = new SellChestManager();
		sellChestManager.saveChest(e.getBlock().getLocation(), e.getPlayer().getUniqueId());

		Bukkit.broadcastMessage("yes");
	}
}
