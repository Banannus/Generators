package dk.banannus.generators.data.file;

import dk.banannus.generators.data.player.PlayerData;
import dk.banannus.generators.data.player.PlayerDataManager;
import dk.banannus.generators.data.sellchest.SellChestItem;
import dk.banannus.generators.data.sellchest.SellChestManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LoadPlayerFiles {


	public void loadPlayerFilesAsync(Plugin plugin) {
		CompletableFuture.runAsync(() -> loadPlayerFiles(plugin)).thenRun(() -> {
			System.out.println("Generators - Player files has been loaded.");
		});
	}

	public void loadPlayerFiles(Plugin plugin) {
		try {
			File playersFolder = new File(plugin.getDataFolder(), "players");
			if (!playersFolder.exists() || !playersFolder.isDirectory()) {
				return;
			}

			File[] playerFiles = playersFolder.listFiles();
			System.out.println(Arrays.toString(playersFolder.listFiles()));
			if (playerFiles == null) {
				return;
			}

			if (playerFiles.length == 0) {
				return;
			}

			for (File playerFile : playerFiles) {
				String fileName = playerFile.getName();
				if (fileName.endsWith(".yml")) {
					UUID uuid = UUID.fromString(fileName.substring(0, fileName.length() - 4));
					System.out.println(uuid);
					FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

					HashSet<PlayerData> playerDataManagerHashSet = new HashSet<>();
					ConfigurationSection gensSection = playerConfig.getConfigurationSection("gens");
					if(gensSection == null) {
						continue;
					}
					for (String key : gensSection.getKeys(false)) {
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
					PlayerDataManager.getAllPlayerDataList().put(uuid, playerDataManagerHashSet);

					ConfigurationSection itemsSection = playerConfig.getConfigurationSection("sellchest");

					if(itemsSection == null) {
						continue;
					}

					HashSet<SellChestItem> sellChestItemHashSet = new HashSet<>();
					for (String key : itemsSection.getKeys(false)) {
						ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
						String genKey = itemSection.getString("key");
						int itemAmount = itemSection.getInt("amount");

						SellChestItem sellChestItem = new SellChestItem(genKey, itemAmount);
						sellChestItemHashSet.add(sellChestItem);
					}
					SellChestManager.getSellChestItems().put(uuid, sellChestItemHashSet);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
