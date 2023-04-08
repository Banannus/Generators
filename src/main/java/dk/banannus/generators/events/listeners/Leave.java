package dk.banannus.generators.events.listeners;

import dk.banannus.generators.data.player.PlayerDataManager;
import dk.banannus.generators.data.sellchest.SellChestManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class Leave implements Listener {

	private final PlayerDataManager playerDataManager;

	public Leave() {
		this.playerDataManager = new PlayerDataManager();
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		SellChestManager sellChestManager = new SellChestManager();
		sellChestManager.unLoadSellChest(uuid);
		PlayerDataManager.unloadPlayerData(uuid, true);
	}
}
