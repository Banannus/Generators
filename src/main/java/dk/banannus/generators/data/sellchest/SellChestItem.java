package dk.banannus.generators.data.sellchest;

import dk.banannus.generators.Generators;
import dk.banannus.generators.data.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SellChestItem {
	private final int amount;
	private final String key;

	public SellChestItem(String key, int amount) {
		this.amount = amount;
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public int getAmount() {
		return amount;
	}

	public void saveSellChestItems(UUID uuid) {
		FileManager fileManager = new FileManager(Generators.instance);
		File file = fileManager.getPlayerFile(uuid);

		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection itemsSection = config.getConfigurationSection("sellchest");

		int maxIndex = 0;
		if (itemsSection != null) {
			for (String key : itemsSection.getKeys(false)) {
				int index = Integer.parseInt(key);
				if (index > maxIndex) {
					maxIndex = index;
				}
			}
		}

		String newIndex = String.valueOf(maxIndex + 1);
		Bukkit.broadcastMessage("newIndex - " + newIndex);

		ConfigurationSection sellchestData = config.getConfigurationSection("sellchest." + newIndex);
		if (sellchestData == null) {
			sellchestData = config.createSection("sellchest." + newIndex);
		}


		sellchestData.set("key", getKey());
		sellchestData.set("amount", getAmount());

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
