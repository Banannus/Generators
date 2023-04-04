package dk.banannus.generators.Data;

import dk.banannus.generators.Generators;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileManager {

	private final Generators plugin;

	public FileManager(Generators plugin) {
		this.plugin = plugin;
	}

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
			} catch (IOException IO) {
				IO.printStackTrace();
			}
		}
	}

	public File getPlayerFile(UUID uuid) {
		File playersFolder = new File(plugin.getDataFolder(), "players");
		if (!playersFolder.exists()) {
			playersFolder.mkdirs();
		}
		return new File(playersFolder, uuid + ".yml");
	}

	public void deleteFile(UUID uuid) {
		FileManager fileManager = new FileManager(Generators.instance);
		File file = fileManager.getPlayerFile(uuid);

		if (file.exists()) {
			file.delete();
		}

		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
