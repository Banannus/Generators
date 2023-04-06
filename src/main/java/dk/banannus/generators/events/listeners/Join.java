package dk.banannus.generators.events.listeners;

import dk.banannus.generators.data.file.FileManager;
import dk.banannus.generators.data.player.PlayerDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import dk.banannus.generators.Generators;

import java.util.UUID;

public class Join implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		FileManager fileManager = new FileManager(Generators.instance);
		fileManager.createPlayerFile(uuid);
		PlayerDataManager.getOfflinePlayerDataList().remove(uuid);
		PlayerDataManager playerDataManager = new PlayerDataManager();
		playerDataManager.loadPlayerData(uuid);
	}
}