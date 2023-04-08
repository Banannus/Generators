package dk.banannus.generators.data.file;

import dk.banannus.generators.Generators;
import dk.banannus.generators.data.player.PlayerData;
import dk.banannus.generators.data.player.PlayerDataManager;
import dk.banannus.generators.data.sellchest.SellChestItem;
import dk.banannus.generators.data.sellchest.SellChestManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class FileManager {

	private final Generators plugin;

	public FileManager(Generators plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public void createPlayerFile(UUID uuid) {
		File playersFolder = new File(plugin.getDataFolder(), "players");
		if (!playersFolder.exists()) {
			playersFolder.mkdirs();
		}

		File playerFile = new File(playersFolder, uuid + ".yml");

		if (!playerFile.exists()) {
			try {
				playerFile.createNewFile();
				plugin.getLogger().info("Oprettede fil for: " + uuid);
				YamlConfiguration playerYaml = YamlConfiguration.loadConfiguration(playerFile);
				playerYaml.set("slots", Integer.valueOf(ConfigManager.get("numbers.start-gens")[0]));
				playerYaml.save(playerFile);
			} catch (IOException IO) {
				IO.printStackTrace();
			}
		}
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public File getPlayerFile(UUID uuid) {
		File playersFolder = new File(plugin.getDataFolder(), "players");
		if (!playersFolder.exists()) {
			playersFolder.mkdirs();
		}
		return new File(playersFolder, uuid + ".yml");
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public void deleteFile(UUID uuid) {
		FileManager fileManager = new FileManager(Generators.instance);
		File file = fileManager.getPlayerFile(uuid);

		if (file.exists()) {
			file.delete();
		}

		try {
			file.createNewFile();
			YamlConfiguration playerYaml = YamlConfiguration.loadConfiguration(file);
			playerYaml.set("slots", Integer.valueOf(ConfigManager.get("numbers.start-gens")[0]));
			playerYaml.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
