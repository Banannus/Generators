package dk.banannus.generators.data.player;

import dk.banannus.generators.data.file.FileManager;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlayerData {

	private String key;
	private Location location;
	private String block;
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

	public static void savePlayerGenData(UUID uuid, Set<PlayerData> playerDataManagerSet) {
		FileManager fileManager = new FileManager();
		File file = fileManager.getPlayerFile(uuid);

		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		int i = 0;
		for (PlayerData playerData : playerDataManagerSet) {

			String newIndex = String.valueOf(i + 1);

			ConfigurationSection blockData = config.getConfigurationSection("gens." + newIndex);
			if (blockData == null) {
				blockData = config.createSection("gens." + newIndex);
			}
			blockData.set("key", playerData.getKey());
			blockData.set("location.world", playerData.getLocation().getWorld().getName());
			blockData.set("location.x", playerData.getLocation().getX());
			blockData.set("location.y", playerData.getLocation().getY());
			blockData.set("location.z", playerData.getLocation().getZ());
			i++;
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
