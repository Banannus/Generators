package dk.banannus.generators.data.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class MultiplierManager {


	private static double globalMultiplier = 1.0;
	private static HashMap<UUID, Double> playerMultipliers = new HashMap<>();

	public static double getGlobalMultiplier()  {
		return globalMultiplier;
	}

	public static void setGlobalMultiplier(double globalMultiplier) {
		MultiplierManager.globalMultiplier = globalMultiplier;
	}

	public static double getPlayerMultiplier(UUID uuid) {
		return playerMultipliers.getOrDefault(uuid, 1.0);
	}

	public static void setPlayerMultiplier(UUID playerUUID, double playerMultiplier) {
		playerMultipliers.put(playerUUID, playerMultiplier);
	}

	public static void addPlayerMultiplier(UUID uuid, double amount) {
		double currentMultiplier =  playerMultipliers.get(uuid);
		double newMultiplier = currentMultiplier + amount;
		setPlayerMultiplier(uuid, newMultiplier);
	}

	public static void removePlayerMultiplier(UUID uuid, double amount) {
		double currentMultiplier =  playerMultipliers.get(uuid);
		double newMultiplier = currentMultiplier - amount;
		setPlayerMultiplier(uuid, newMultiplier);
	}
}
