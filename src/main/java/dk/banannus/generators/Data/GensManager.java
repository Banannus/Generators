package dk.banannus.generators.Data;

import dk.banannus.generators.Generators;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GensManager {

	public static HashMap<String, Gen> genValues = new HashMap<>();
	public static void addGen(String key, Gen data) {
		genValues.put(key, data);
	}

	public static HashMap<String, Gen> getGenList() {
		return genValues;
	}

	public Gen getGen(String key) {
		return genValues.get(key);
	}

	public static HashMap<ItemStack, String> getGenBlocks() {
		ConfigurationSection gensSection = Generators.gensYML.getConfigurationSection("gens");

		HashMap<ItemStack, String> materials = new HashMap<>();
		for(String key : gensSection.getKeys(false)) {
			String block = gensSection.getString(key + ".block");
			if(!block.contains(":")) {
				ItemStack item = new ItemStack(Material.valueOf(block));
				materials.put(item, key);
			} else {
				String[] parts = block.split(":");
				ItemStack item = new ItemStack(Material.valueOf(parts[0]), 1, (short) Integer.parseInt(parts[1]));
				materials.put(item, key);
			}
		}
		return materials;
	}


	public static void loadGens() {
		ConfigurationSection gensSection = Generators.gensYML.getConfigurationSection("gens");

		for (String number : gensSection.getKeys(false)) {
			ConfigurationSection section = gensSection.getConfigurationSection(number);

			String block = section.getString("block");
			String name = section.getString("name");
			String drop = section.getString("drop");
			int salgspris = section.getInt("salgspris");
			double xp = section.getDouble("xp");
			int upgradepris = section.getInt("upgradepris");

			Gen data = new Gen(block, name, drop, salgspris, xp, upgradepris);
			addGen(number, data);
		}
	}

	public static String getKeyFromBlock(String block) {
		for (Map.Entry<String, Gen> entry : genValues.entrySet()) {
			if (entry.getValue().getBlock().equals(block)) {
				return entry.getKey();
			}
		}
		return null;
	}
}