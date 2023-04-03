package dk.banannus.generators.GenData;

import dk.banannus.generators.Generators;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GensManager {

	public static HashMap<String, GetGenValue> genValues = new HashMap<>();
	public static List<String> genBlocks = new ArrayList<>();
	public static void addGen(String key, GetGenValue data) {
		genValues.put(key, data);
	}

	public static HashMap<String, GetGenValue> getGenList() {
		return genValues;
	}

	public static GetGenValue getGen(String key) {
		return genValues.get(key);
	}

	public static List<String> getGenBlocks() {
		ConfigurationSection gensSection = Generators.gensYML.getConfigurationSection("gens");

		List<String> materials = new ArrayList<>();
		for(String key : gensSection.getKeys(false)) {
			Bukkit.broadcastMessage(key);
			if(key.contains(":")) {
				String[] parts = key.split(":");
				ItemStack item = new ItemStack(Material.valueOf(parts[0]), 1, (short) Integer.parseInt(parts[1]));
				GetGenValue genValue = genValues.get(String.valueOf(item));
				String blockName = genValue.getBlock();
				materials.add(String.valueOf(blockName));
				Bukkit.broadcastMessage(blockName);
			} else {
				GetGenValue genValue = genValues.get(key);
				String blockName = genValue.getBlock();
				materials.add(String.valueOf(blockName));
				Bukkit.broadcastMessage(blockName);
			}
		}
		Bukkit.broadcastMessage(materials.toString());
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

			GetGenValue data = new GetGenValue(block, name, drop, salgspris, xp, upgradepris);
			addGen(number, data);
		}
	}
}
