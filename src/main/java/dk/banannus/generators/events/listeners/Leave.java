package dk.banannus.generators.events.listeners;

import dk.banannus.generators.data.player.PlayerDataManager;
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
		PlayerDataManager.unloadPlayerData(uuid, true);
	}
}
