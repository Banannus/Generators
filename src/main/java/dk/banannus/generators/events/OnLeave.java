package dk.banannus.generators.events;

import dk.banannus.generators.data.player.PlayerDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class OnLeave implements Listener {

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		PlayerDataManager.unloadPlayerData(uuid);
	}
}
