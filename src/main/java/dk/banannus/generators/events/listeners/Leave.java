package dk.banannus.generators.events.listeners;

import dk.banannus.generators.data.cache.PlayerCache;
import dk.banannus.generators.data.cache.PlayerCacheManager;
import dk.banannus.generators.data.player.PlayerDataManager;
import dk.banannus.generators.data.sellchest.SellChestManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class Leave implements Listener {

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		PlayerCacheManager.startCache(uuid);

	}
}
