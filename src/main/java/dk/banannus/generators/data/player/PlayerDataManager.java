package dk.banannus.generators.data.player;

import dk.banannus.generators.Generators;
import dk.banannus.generators.data.file.FileManager;
import dk.banannus.generators.data.gen.Gen;
import dk.banannus.generators.data.gen.GensManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;


public class PlayerDataManager {

	private static HashMap<UUID, Set<PlayerData>> onlinePlayerData = new HashMap<>();

	private static HashMap<UUID, Set<PlayerData>> offlinePlayerData = new HashMap<>();

	private static HashMap<UUID, Set<PlayerData>> allPlayerData = new HashMap<>();

	public static HashMap<UUID, Set<PlayerData>> getOnlinePlayerDataList() {
		return onlinePlayerData;
	}

	public static HashMap<UUID, Set<PlayerData>> getOfflinePlayerDataList() {
		return offlinePlayerData;
	}

	public static HashMap<UUID, Set<PlayerData>> getAllPlayerDataList() {
		return allPlayerData;
	}


	public HashMap<Location, String> getAllPlayerLocations (UUID uuid) {
		HashMap<Location, String> allLocations = new HashMap<>();

		if(!allPlayerData.containsKey(uuid)) {
			return null;
		}

		Set<PlayerData> playerData = allPlayerData.get(uuid);

		for(PlayerData index : playerData) {
			Location genLoc = index.getLocation();
			String key = index.getKey();
			allLocations.put(genLoc, key);
		}
		return allLocations;
	}

	public List<Location> getAllLocations() {

		List<Location> allLocations = new ArrayList<>();

		for (UUID uuid : allPlayerData.keySet()) {
			Set<PlayerData> playerData = allPlayerData.get(uuid);
			for (PlayerData data : playerData) {
				Location location = data.getLocation();
				allLocations.add(location);
			}
		}
		return allLocations;
	}

	public void saveGen(String key, Location location, UUID uuid) {
		HashMap<String, Gen> genValues = GensManager.getGenList();
		Gen genValue = genValues.get(key);
		String block = genValue.getBlock();
		String name = genValue.getName();

		PlayerData playerDataManager = new PlayerData(key, location, block, name);

		Set<PlayerData> playerDataManagerSet = onlinePlayerData.getOrDefault(uuid, new HashSet<>());
		playerDataManagerSet.add(playerDataManager);
		onlinePlayerData.put(uuid, playerDataManagerSet);
		allPlayerData.put(uuid, playerDataManagerSet);
	}

	public void removeGen(Location location, UUID uuid) {
		if(!onlinePlayerData.get(uuid).isEmpty()) {
			onlinePlayerData.computeIfPresent(uuid, (key, value) -> {
				value.removeIf(playerData -> playerData.getLocation().equals(location));
				return value;
			});
			allPlayerData.computeIfPresent(uuid, (key, value) -> {
				value.removeIf(playerData -> playerData.getLocation().equals(location));
				return value;
			});
			if(onlinePlayerData.get(uuid).isEmpty()) {
				unloadPlayerData(uuid, false);
			}
			if(allPlayerData.get(uuid).isEmpty()) {
				allPlayerData.remove(uuid);
			}
		} else {
			unloadPlayerData(uuid, false);
		}
	}

	public void saveAll(UUID uuid) {
		Set<PlayerData> playerDataManager = onlinePlayerData.get(uuid);
		FileManager fileManager = new FileManager(Generators.instance);
		if (playerDataManager == null) {
			fileManager.deleteFile(uuid);
			return;
		}
		fileManager.deleteFile(uuid);
		for (PlayerData playerData : playerDataManager) {
			playerData.savePlayerGenData(uuid);
		}
	}

	public void loadPlayerData(UUID uuid) {
		FileManager fileManager = new FileManager(Generators.instance);
		File file = fileManager.getPlayerFile(uuid);

		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		HashSet<PlayerData> playerDataManagerHashSet = new HashSet<>();
		ConfigurationSection gensSection = config.getConfigurationSection("gens");
		if(gensSection == null) {
			return;
		}
		for(String key : gensSection.getKeys(false)) {
			ConfigurationSection genSection = gensSection.getConfigurationSection(key);
			String genKey = genSection.getString("key");
			String worldName = genSection.getString("location.world");
			double x = genSection.getDouble("location.x");
			double y = genSection.getDouble("location.y");
			double z = genSection.getDouble("location.z");
			String block = genSection.getString("block");
			String name = genSection.getString("name");

			Location location = new Location(Bukkit.getWorld(worldName), x, y, z);

			PlayerData playerDataManager = new PlayerData(genKey, location, block, name);
			playerDataManagerHashSet.add(playerDataManager);
		}

		offlinePlayerData.remove(uuid);
		onlinePlayerData.put(uuid, playerDataManagerHashSet);
		allPlayerData.put(uuid, playerDataManagerHashSet);
	}

	public void unloadPlayerData(UUID uuid, boolean all) {
		if(all) {
			offlinePlayerData.put(uuid, onlinePlayerData.get(uuid));
			allPlayerData.put(uuid, onlinePlayerData.get(uuid));
		}
		onlinePlayerData.remove(uuid);
	}

	public static int getAmountOfPlacedGens(UUID uuid) {

		Set<PlayerData> playerDataList = PlayerDataManager.getOnlinePlayerDataList().get(uuid);
		if (playerDataList != null) {
			return playerDataList.size();
		} else {
			return 0;
		}
	}
}
