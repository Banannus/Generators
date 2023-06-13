package dk.banannus.generators.data.cache;

import dk.banannus.generators.data.player.PlayerData;
import dk.banannus.generators.data.sellchest.SellChestItem;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public class PlayerCache {
	private final Set<PlayerData> playerData;
	private final Set<SellChestItem> sellChestItems;
	private final Location location;
	private final double slots;
	private final double xp;
	private final double multi;

	public PlayerCache(Set<PlayerData> playerData, Set<SellChestItem> sellChestItems, Location location, double slots, double xp, double multi) {
		this.playerData = playerData;
		this.sellChestItems = sellChestItems;
		this.location = location;
		this.slots = slots;
		this.xp = xp;
		this.multi = multi;
	}

	public Set<PlayerData> getPlayerData() {
		return playerData;
	}

	public Set<SellChestItem> getSellChestItems() {
		return sellChestItems;
	}

	public Location getLocation() {
		return location;
	}
	public double getSlots() {
		return slots;
	}

	public double getXP() {
		return xp;
	}

	public double getMulti() {
		return multi;
	}
}
