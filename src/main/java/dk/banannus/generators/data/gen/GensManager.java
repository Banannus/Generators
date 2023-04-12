package dk.banannus.generators.data.gen;

import dk.banannus.generators.Generators;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GensManager {

	private static final HashMap<String, Gen> genValues = new HashMap<>();
	private static final HashMap<ItemStack, String> materials = new HashMap<>();
	public static void addGen(String key, Gen data) {
		genValues.put(key, data);
	}

	public static HashMap<String, Gen> getGenList() {
		return genValues;
	}

	public static HashMap<ItemStack, String> getGenBlockList() {
		return materials;
	}

	public static void loadGenBlocks() {
		for(Gen gen : genValues.values()) {
			String block = gen.getBlock();
			String key = gen.getKey();
			if(!block.contains(":")) {
				ItemStack item = new ItemStack(Material.valueOf(block));
				materials.put(item, key);
			} else {
				String[] parts = block.split(":");
				ItemStack item = new ItemStack(Material.valueOf(parts[0]), 1, (short) Integer.parseInt(parts[1]));
				materials.put(item, key);
			}
		}
	}

	public static HashMap<String, ItemStack> ReverseGenBlocksList() {
		HashMap<String, ItemStack> reverseMaterials = new HashMap<>();

		for (ItemStack item : materials.keySet()) {
			String key = materials.get(item);
			reverseMaterials.put(key, item);
		}
		return reverseMaterials;
	}

	public static List<String> getGenNames() {

		List<String> names = new ArrayList<>();
		for(Gen gen : genValues.values()) {
			String name = gen.getName();
			names.add(name);
		}
		return names;
	}

	public static ItemStack getDrop(String key) {
		Gen gen = GensManager.getGenList().get(key);
		String drop = gen.getDrop();
		if (!drop.contains(":")) {
			return new ItemStack(Material.valueOf(drop));
		} else {
			String[] parts = drop.split(":");
			return new ItemStack(Material.valueOf(parts[0]), 1, (short) Integer.parseInt(parts[1]));
		}
	}

	public static void loadGens() {
		ConfigurationSection gensSection = Generators.gensYML.getConfigurationSection("gens");

		for (String number : gensSection.getKeys(false)) {
			ConfigurationSection section = gensSection.getConfigurationSection(number);

			String block = section.getString("block");
			String name = section.getString("name");
			String drop = section.getString("drop");
			String dropName = section.getString("dropName");
			int salgspris = section.getInt("salgspris");
			double xp = section.getDouble("xp");
			int upgradepris = section.getInt("upgradepris");

			Gen data = new Gen(number, block, name, drop, dropName, salgspris, xp, upgradepris);
			addGen(number, data);
		}
	}

	public static boolean isGenNotBlock(Material clickedBlock) {
		return !GensManager.getGenBlockList().containsKey(new ItemStack(clickedBlock));
	}
}
