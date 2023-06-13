package dk.banannus.generators.events.custom.listeners;

import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.sellchest.SellChestManager;
import dk.banannus.generators.events.custom.events.SellChestPlaceEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class SellChestPlace implements Listener {

	@EventHandler (priority =  EventPriority.HIGHEST)
	public void onSellChestPlace(SellChestPlaceEvent e) {

		if(e.isCancelled()) {
			e.setCancelled(true);
			return;
		}

		Player player = e.getPlayer();;

		SellChestManager sellChestManager = new SellChestManager();
		sellChestManager.saveChest(e.getBlock().getLocation(), player.getUniqueId());

		ConfigManager.send(player, "messages.place-sell-chest");
	}
}
