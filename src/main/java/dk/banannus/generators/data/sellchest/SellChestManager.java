package dk.banannus.generators.data.sellchest;

import dk.banannus.generators.Generators;
import dk.banannus.generators.data.file.FileManager;
import dk.banannus.generators.data.gen.Gen;
import dk.banannus.generators.data.gen.GensManager;
import dk.banannus.generators.data.player.PlayerData;
import dk.banannus.generators.data.player.PlayerDataManager;
import dk.banannus.generators.data.player.XpManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SellChestManager {

	private static HashMap<UUID, Set<SellChestItem>> sellChestItems = new HashMap<>();
	private static HashMap<UUID, Integer> genTasks = new HashMap<>();
	private static HashMap<UUID, Location> sellChestPlayerLocation = new HashMap<>();

	public static synchronized HashMap<UUID, Set<SellChestItem>> getSellChestItems() {
		return sellChestItems;
	}

	public static synchronized HashMap<UUID, Location> getSellChestPlayerLocationList() {
		return sellChestPlayerLocation;
	}

	private static List<BukkitRunnable> sellChestItemsChangeListeners = new ArrayList<>();

	public static void addSellChestItemsChangeListener(BukkitRunnable listener) {
		sellChestItemsChangeListeners.add(listener);
	}

	public static void removeSellChestItemsChangeListener(BukkitRunnable  listener) {
		sellChestItemsChangeListeners.remove(listener);
	}

	public static void fireSellChestItemsChangedEvent() {
		List<BukkitRunnable> listenersCopy = new ArrayList<>(sellChestItemsChangeListeners);
		for (BukkitRunnable listener : listenersCopy) {
			if(listener != null) {
				listener.run();
			}
		}
	}

	public static synchronized void removeSellChestItem(UUID uuid, SellChestItem sellChestItem) {
		Set<SellChestItem> sellChestItemsForPlayer = sellChestItems.get(uuid);
		if (sellChestItemsForPlayer != null) {
			sellChestItemsForPlayer.remove(sellChestItem);
		}
	}

	public static List<Location> getAllLocations() {

		List<Location> allLocations = new ArrayList<>();

		for (UUID uuid : sellChestPlayerLocation.keySet()) {
			Location loc = sellChestPlayerLocation.get(uuid);
			allLocations.add(loc);
		}
		return allLocations;
	}

	public void startGenDrops(UUID uuid) {
		int taskId = Bukkit.getScheduler().runTaskTimer(Generators.instance, new Runnable() {
			public void run() {
				Set<PlayerData> playerDataSet = PlayerDataManager.getOnlinePlayerDataList().get(uuid);
				if(playerDataSet == null) {
					return;
				}
				Set<SellChestItem> sellChestItems = SellChestManager.sellChestItems.computeIfAbsent(uuid, k -> new HashSet<>());
				for (PlayerData playerData : playerDataSet) {
					String key = playerData.getKey();
					boolean found = false;
					for (SellChestItem genItem : sellChestItems) {
						if (genItem.getKey().equals(key)) {
							sellChestItems.remove(genItem);
							genItem = new SellChestItem(key, genItem.getAmount() + 1);
							sellChestItems.add(genItem);
							found = true;
							break;
						}
					}
					if (!found) {
						SellChestItem sellChestItem = new SellChestItem(key, 1);
						sellChestItems.add(sellChestItem);
					}
				}
				SellChestManager.fireSellChestItemsChangedEvent();
			}
		}, 0, 100).getTaskId();
		genTasks.put(uuid, taskId);
	}

	public void cancelGenDrops(UUID uuid) {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		int taskId = genTasks.getOrDefault(uuid, -1);
		if (taskId != -1) {
			scheduler.cancelTask(taskId);
			genTasks.remove(uuid);
		}
	}

	public void loadSellChestItems(UUID uuid) {
		FileManager fileManager = new FileManager();
		File file = fileManager.getPlayerFile(uuid);

		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		ConfigurationSection itemsSection = config.getConfigurationSection("sellchest");
		HashSet<SellChestItem> sellChestItemHashSet = new HashSet<>();
		if(itemsSection == null) {
			return;
		}
		for(String key : itemsSection.getKeys(false)) {
			ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
			String genKey = itemSection.getString("key");
			int itemAmount = itemSection.getInt("amount");

			SellChestItem sellChestItem = new SellChestItem(genKey, itemAmount);
			sellChestItemHashSet.add(sellChestItem);
		}
		sellChestItems.put(uuid, sellChestItemHashSet);
	}

	public void saveChest(Location location, UUID uuid) {
		sellChestPlayerLocation.put(uuid, location);
	}

	public void saveSellChest(UUID uuid) {
		FileManager fileManager = new FileManager();
		File file = fileManager.getPlayerFile(uuid);

		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection chestSection = config.getConfigurationSection("sellchestLoc");

		if (chestSection == null) {
			chestSection = config.createSection("sellchestLoc");
		}
		Location location = sellChestPlayerLocation.get(uuid);
		if(location == null) {
			return;
		}
		chestSection.set("location.world", location.getWorld().getName());
		chestSection.set("location.x", location.getX());
		chestSection.set("location.y", location.getY());
		chestSection.set("location.z", location.getZ());

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void loadSellChest(UUID uuid) {
		FileManager fileManager = new FileManager();
		File file = fileManager.getPlayerFile(uuid);

		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		ConfigurationSection sellChestLocSection = config.getConfigurationSection("sellchestLoc");
		if(sellChestLocSection == null) {
			return;
		}
		ConfigurationSection locationSection = sellChestLocSection.getConfigurationSection("location");
		if(locationSection == null) {
			return;
		}
		String worldName = locationSection.getString("world");
		double x = locationSection.getDouble("x");
		double y = locationSection.getDouble("y");
		double z = locationSection.getDouble("z");

		Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
		sellChestPlayerLocation.put(uuid, location);
	}

	public void unLoadSellChest(UUID uuid) {
		sellChestPlayerLocation.remove(uuid);
	}
}

