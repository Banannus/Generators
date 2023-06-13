package dk.banannus.generators.data.cache;

import dk.banannus.generators.Generators;
import dk.banannus.generators.data.player.*;
import dk.banannus.generators.data.sellchest.SellChestItem;
import dk.banannus.generators.data.sellchest.SellChestManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class PlayerCacheManager {

	private static final HashMap<UUID, PlayerCache> playerCacheMap  = new HashMap<>();
	private static HashMap<UUID, BukkitTask> cacheTasks = new HashMap<>();

	public static PlayerCache getPlayerCache(UUID uuid) {
		return playerCacheMap.get(uuid);
	}

	public static HashMap<UUID, PlayerCache> getCacheMap() {
		return playerCacheMap;
	}

	public static void savePlayerCache(UUID uuid) {
		Set<PlayerData> playerData = PlayerDataManager.getOnlinePlayerDataList().get(uuid);
		Set<SellChestItem> sellChestItems = SellChestManager.getSellChestItems().get(uuid);
		Location location = SellChestManager.getSellChestPlayerLocationList().get(uuid);
		double xp = XpManager.getXP(uuid);
		double slots = SlotsManager.getSlots(uuid);
		double multi = MultiplierManager.getPlayerMultiplier(uuid);
		PlayerCache playerCache = new PlayerCache(playerData, sellChestItems, location, slots, xp, multi);
		playerCacheMap.put(uuid, playerCache);

	}

	public static void startCache(UUID uuid) {
		if(cacheTasks.containsKey(uuid)) {
			cacheTasks.get(uuid).cancel();
			cacheTasks.remove(uuid);
		}
		savePlayerCache(uuid);
		PlayerCache cache = playerCacheMap.get(uuid);

;		if(cache != null) {
			BukkitTask task = new BukkitRunnable() {
				@Override
				public void run() {
					if(Bukkit.getPlayer(uuid) == null) {
						PlayerDataManager playerDataManager = new PlayerDataManager();
						SellChestManager sellChestManager = new SellChestManager();

						playerDataManager.savePlayerData(uuid);
						sellChestManager.saveSellChest(uuid);
						sellChestManager.unLoadSellChest(uuid);
						sellChestManager.cancelGenDrops(uuid);
						playerDataManager.unloadPlayerData(uuid, true);

						playerCacheMap.remove(uuid);
						Bukkit.broadcastMessage("yes");
					} else {
						playerCacheMap.remove(uuid);
						Bukkit.broadcastMessage("no");
					}
				}
			}.runTaskLater(Generators.getInstance(), 200L);
			cacheTasks.put(uuid, task);
		}
	}
}
