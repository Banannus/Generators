package dk.banannus.generators.data.player;

import dk.banannus.generators.Generators;
import dk.banannus.generators.data.file.FileManager;
import dk.banannus.generators.data.gen.Gen;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static dk.banannus.generators.data.gen.GensManager.genValues;
import static dk.banannus.generators.data.player.PlayerData.playerDataValues;

public class PlayerDataManager {

	public  HashMap<Location, String> getAllLocations(UUID uuid) {
		HashMap<Location, String> allLocations = new HashMap<>();

		Set<PlayerData> playerDataManager = playerDataValues.get(uuid);

		for(PlayerData index : playerDataManager) {
			Location genLoc = index.getLocation();
			String key = index.getKey();
			allLocations.put(genLoc, key);
		}
		return allLocations;
	}

	public void saveGen(String key, Location location, UUID uuid) {
		Gen genValue = genValues.get(key);
		String block = genValue.getBlock();
		String name = genValue.getName();

		PlayerData playerDataManager = new PlayerData(key, location, block, name);

		Set<PlayerData> playerDataManagerSet = playerDataValues.getOrDefault(uuid, new HashSet<>());
		playerDataManagerSet.add(playerDataManager);
		playerDataValues.put(uuid, playerDataManagerSet);
	}

	public void removeGen(Location location, UUID uuid) {
		if(!playerDataValues.get(uuid).isEmpty()) {
			playerDataValues.computeIfPresent(uuid, (key, value) -> {
				value.removeIf(playerData -> playerData.getLocation().equals(location));
				return value;
			});
			if(playerDataValues.get(uuid).isEmpty()) {
				unloadPlayerData(uuid);
			}
		} else {
			unloadPlayerData(uuid);
		}
	}

	public void saveAll(UUID uuid) {
		Set<PlayerData> playerDataManager = playerDataValues.get(uuid);
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
	}

	public static void unloadPlayerData(UUID uuid) {
		playerDataValues.remove(uuid);
	}

}
