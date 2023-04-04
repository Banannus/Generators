package dk.banannus.generators.Data;

import dk.banannus.generators.Generators;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static dk.banannus.generators.Data.GensManager.genValues;

public class PlayerData {

	private final String key;
	private Location location;
	private String block;

	public static HashMap<UUID, Set<PlayerData>> playerDataValues = new HashMap<>();

	private String name;

	public PlayerData(String key, Location location, String block, String name) {
		this.key = key;
		this.location = location;
		this.block = block;
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public Location getLocation() {
		return location;
	}

	public String getBlock() {
		return block;
	}

	public String getName() {
		return name;
	}


	public static HashMap<Location, String> getAllLocations(UUID uuid) {
		HashMap<Location, String> allLocations = new HashMap<>();

		Set<PlayerData> playerDataManager = playerDataValues.get(uuid);

		for(PlayerData index : playerDataManager) {
			Location genLoc = index.getLocation();
			String key = index.getKey();
			Bukkit.broadcastMessage(key);
			allLocations.put(genLoc, key);
		}
		return allLocations;
	}


	public static void saveGen(String key, Location location, UUID uuid) {
		Gen genValue = genValues.get(key);
		String block = genValue.getBlock();
		String name = genValue.getName();

		PlayerData playerDataManager = new PlayerData(key, location, block, name);

		Set<PlayerData> playerDataManagerSet = playerDataValues.getOrDefault(uuid, new HashSet<>());
		playerDataManagerSet.add(playerDataManager);
		playerDataValues.put(uuid, playerDataManagerSet);
	}

	public static void removeGen(Location location, UUID uuid) {
		playerDataValues.computeIfPresent(uuid, (key, value) -> {
			value.removeIf(playerData -> playerData.getLocation().equals(location));
			return value;
		});
	}

	public static void saveAll(UUID uuid) {
		Set<PlayerData> playerDataManager = playerDataValues.get(uuid);
		FileManager fileManager = new FileManager(Generators.instance);
		if (playerDataManager == null) {
			fileManager.deleteFile(uuid);
			return;
		}
		fileManager.deleteFile(uuid);
		for (PlayerData playerData : playerDataManager) {
			playerData.saveGenData(uuid);
		}
	}

	public void saveGenData(UUID uuid) {
		FileManager fileManager = new FileManager(Generators.instance);
		File file = fileManager.getPlayerFile(uuid);

		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		int maxIndex = 0;
		ConfigurationSection gensSection = config.getConfigurationSection("gens");
		if (gensSection != null) {
			for (String key : gensSection.getKeys(false)) {
				int index = Integer.parseInt(key);
				if (index > maxIndex) {
					maxIndex = index;
				}
			}
		}
		String newIndex = String.valueOf(maxIndex + 1);

		ConfigurationSection blockData = config.getConfigurationSection("gens." + newIndex);
		if (blockData == null) {
			blockData = config.createSection("gens." + newIndex);
		}
		blockData.set("key", getKey());
		blockData.set("location.world", getLocation().getWorld().getName());
		blockData.set("location.x", getLocation().getX());
		blockData.set("location.y", getLocation().getY());
		blockData.set("location.z", getLocation().getZ());
		blockData.set("block", getBlock());
		blockData.set("name", getName());

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadPlayerData(UUID uuid) {
		FileManager fileManager = new FileManager(Generators.instance);
		File file = fileManager.getPlayerFile(uuid);

		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		HashSet<PlayerData> playerDataManagerHashSet = new HashSet<>();
		ConfigurationSection gensSection = config.getConfigurationSection("gens");
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
		playerDataValues.put(uuid, playerDataManagerHashSet);
		Bukkit.broadcastMessage(String.valueOf(playerDataManagerHashSet));
	}


	public static void unloadPlayerData(UUID uuid) {
		playerDataValues.remove(uuid);
	}
}
