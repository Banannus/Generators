package dk.banannus.generators.events.listeners;

import dk.banannus.generators.events.custom.events.GenPlaceEvent;
import dk.banannus.generators.events.custom.events.SellChestPlaceEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		SellChestPlaceEvent sellChestPlaceEvent = new SellChestPlaceEvent(e.getPlayer(), e.getBlock());
		Bukkit.getPluginManager().callEvent(sellChestPlaceEvent);
		GenPlaceEvent genPlaceEvent = new GenPlaceEvent(e.getPlayer(), e.getBlock());
		Bukkit.getPluginManager().callEvent(genPlaceEvent);
		if (!genPlaceEvent.isBlockPlaceAllowed()) {
			e.setCancelled(true);
		}
	}
}
