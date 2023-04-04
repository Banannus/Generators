package dk.banannus.generators.events;

import dk.banannus.generators.data.file.FileManager;
import dk.banannus.generators.data.player.PlayerDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import dk.banannus.generators.Generators;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class OnFirstJoin implements Listener {

	@EventHandler
	public void onFirstJoin(PlayerJoinEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		FileManager fileManager = new FileManager(JavaPlugin.getPlugin(Generators.class));
		fileManager.createPlayerFile(uuid);
		PlayerDataManager playerDataManager = new PlayerDataManager();
		playerDataManager.loadPlayerData(uuid);
	}
}
