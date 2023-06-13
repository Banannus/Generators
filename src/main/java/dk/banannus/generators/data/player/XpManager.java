package dk.banannus.generators.data.player;

import java.util.HashMap;
import java.util.UUID;

public class XpManager {
	public double getXP(String uuid) {
		return xpList.getOrDefault(UUID.fromString(uuid), 0.0);
	}
	public static HashMap<UUID, Double> xpList = new HashMap<>();
	public static void setXP(UUID uuid, double amount) {
		xpList.put(uuid, amount);
	}
	public static double getXP(UUID uuid) {
		return xpList.getOrDefault(uuid, 0.0);
	}
	public static void addXP(UUID uuid, double amount) {
		setXP(uuid, getXP(uuid) + amount);
	}
	public static void removeXP(UUID uuid, double amount) {
		setXP(uuid, getXP(uuid) - amount);
	}
}
