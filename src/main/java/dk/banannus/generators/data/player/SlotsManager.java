package dk.banannus.generators.data.player;

import dk.banannus.generators.data.file.ConfigManager;

import java.util.HashMap;
import java.util.UUID;

public class SlotsManager {


	public static HashMap<UUID, Double> slots = new HashMap<>();

	public static void setSlots(UUID uuid, double amount) {
		slots.put(uuid, amount);
	}

	public static double getSlots(UUID uuid) {
		return slots.getOrDefault(uuid, Double.valueOf(ConfigManager.get("numbers.start-gens")[0]));
	}



	public static void addSlots(UUID uuid, double amount) {
		setSlots(uuid, getSlots(uuid) + amount);
	}

	public static void removeSlots(UUID uuid, double amount) {
		setSlots(uuid, getSlots(uuid) - amount);
	}
}
