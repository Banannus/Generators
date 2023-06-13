package dk.banannus.generators.events.listeners;

import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.sellchest.SellChestManager;
import dk.banannus.generators.events.custom.events.GenPlaceEvent;
import dk.banannus.generators.events.custom.events.SellChestPlaceEvent;
import dk.banannus.generators.events.custom.events.utils.CancellableEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class BlockPlace implements Listener {

	@EventHandler (priority =  EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent e) {

		if (ConfigManager.get("sell-chest.name")[0].equals(e.getPlayer().getItemInHand().getItemMeta().getDisplayName())) {
			if (SellChestManager.getSellChestPlayerLocationList().get(e.getPlayer().getUniqueId()) == null) {
				CancellableEvent sellChestPlaceEvent = new SellChestPlaceEvent(e.getPlayer(), e.getBlock());
				sellChestPlaceEvent.call();
				e.setCancelled(sellChestPlaceEvent.isCancelled());
			} else {
				ConfigManager.send(e.getPlayer(), "messages.sell-chest-placed");
				e.setCancelled(true);
			}
			return;
		}

		CancellableEvent genPlaceEvent = new GenPlaceEvent(e.getPlayer(), e.getBlock());
		genPlaceEvent.call();
		e.setCancelled(genPlaceEvent.isCancelled());
	}
}
