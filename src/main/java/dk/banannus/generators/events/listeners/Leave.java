package dk.banannus.generators.events.listeners;

import dk.banannus.generators.data.player.PlayerDataManager;
import dk.banannus.generators.data.sellchest.SellChestManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class Leave implements Listener {

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		PlayerDataManager playerDataManager = new PlayerDataManager();
		SellChestManager sellChestManager = new SellChestManager();
		playerDataManager.saveAll(uuid);
		sellChestManager.saveSellChest(uuid);
		sellChestManager.unLoadSellChest(uuid);
		sellChestManager.cancelGenDrops(uuid);
		playerDataManager.unloadPlayerData(uuid, true);

	}
}
