package dk.banannus.generators.events.listeners;

import dk.banannus.generators.data.cache.PlayerCache;
import dk.banannus.generators.data.cache.PlayerCacheManager;
import dk.banannus.generators.data.file.FileManager;
import dk.banannus.generators.data.player.*;
import dk.banannus.generators.data.sellchest.SellChestItem;
import dk.banannus.generators.data.sellchest.SellChestManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Set;
import java.util.UUID;


public class Join implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		PlayerCache playerCache = PlayerCacheManager.getPlayerCache(uuid);
		SellChestManager sellChestManager = new SellChestManager();
		FileManager fileManager = new FileManager();
		fileManager.createPlayerFile(uuid);
		if(playerCache == null) {
			PlayerDataManager playerDataManager = new PlayerDataManager();

			PlayerDataManager.getOfflinePlayerDataList().remove(uuid);
			playerDataManager.loadPlayerData(uuid);
			sellChestManager.loadSellChestItems(uuid);
			sellChestManager.loadSellChest(uuid);
		} else {
			Set<PlayerData> playerData = playerCache.getPlayerData();
			Set<SellChestItem> sellChestItems = playerCache.getSellChestItems();
			Location location = playerCache.getLocation();
			double slots = playerCache.getSlots();
			double xp = playerCache.getXP();
			double multi = playerCache.getMulti();
			PlayerDataManager.getOnlinePlayerDataList().put(uuid, playerData);
			PlayerDataManager.getAllPlayerDataList().put(uuid, playerData);
			PlayerDataManager.getOfflinePlayerDataList().remove(uuid);
			SellChestManager.getSellChestItems().put(uuid, sellChestItems);
			SellChestManager.getSellChestPlayerLocationList().put(uuid, location);
			SellChestManager.getAllLocations().add(location);
			SlotsManager.setSlots(uuid, slots);
			XpManager.setXP(uuid, xp);
			MultiplierManager.setPlayerMultiplier(uuid, multi);
			PlayerCacheManager.getCacheMap().remove(uuid);

		}
		sellChestManager.startGenDrops(uuid);
	}
}
