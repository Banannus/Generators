package dk.banannus.generators.data.file;

import dk.banannus.generators.Generators;
import dk.banannus.generators.data.player.MultiplierManager;
import dk.banannus.generators.data.player.SlotsManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
				playerYaml.set("multi", Integer.valueOf(ConfigManager.get("numbers.start-multiplier")[0]));
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
			playerYaml.set("slots", SlotsManager.getSlots(uuid));
			playerYaml.set("multi", MultiplierManager.getPlayerMultiplier(uuid));
			playerYaml.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
