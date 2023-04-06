package dk.banannus.generators.data.player;

import dk.banannus.generators.data.file.ConfigManager;

import java.util.HashMap;
import java.util.UUID;

public class SlotsManager {

	private static HashMap<UUID, Integer> slots = new HashMap<>();

	public static HashMap<UUID, Integer> getSlotsList() {
		return slots;
	}

	public static void setSlots(UUID uuid, int amount) {
		slots.put(uuid, amount);
	}

	public static int getSlots(UUID uuid) {
		return slots.getOrDefault(uuid, Integer.valueOf(ConfigManager.get("numbers.start-gens")[0]));
	}

	public static void addSlots(UUID uuid, int amount) {
		setSlots(uuid, getSlots(uuid) + amount);
	}
}
