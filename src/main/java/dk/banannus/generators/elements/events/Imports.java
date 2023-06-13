package dk.banannus.generators.elements.events;

import dk.banannus.generators.data.file.ConfigManager;

import java.util.UUID;

import static dk.banannus.generators.data.player.MultiplierManager.playerMultipliers;
import static dk.banannus.generators.data.player.SlotsManager.slots;
import static dk.banannus.generators.data.player.XpManager.xpList;


public class Imports {

	public static double getPlayerMultiplier(String uuid) {
		return playerMultipliers.getOrDefault(UUID.fromString(uuid), 1.0);
	}

	public static double getSlots(String uuid) {
		return slots.getOrDefault(UUID.fromString(uuid), Double.valueOf(ConfigManager.get("numbers.start-gens")[0]));
	}

	public static double getXP(String uuid) {
		return xpList.getOrDefault(UUID.fromString(uuid), 0.0);
	}
}
