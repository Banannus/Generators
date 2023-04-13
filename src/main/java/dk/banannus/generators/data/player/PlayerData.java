package dk.banannus.generators.data.player;

import dk.banannus.generators.Generators;
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

	public void savePlayerGenData(UUID uuid) {
		FileManager fileManager = new FileManager(Generators.instance);
		File file = fileManager.getPlayerFile(uuid);

		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		ConfigurationSection gensSection = config.getConfigurationSection("gens");

		int maxIndex = 0;
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

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
