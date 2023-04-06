package dk.banannus.generators.data.sellchest;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class SellChest {
	private final String sellchest;
	private final Location location;
	private final ItemStack item;


	public SellChest(String sellchest, Location location, ItemStack item) {
		this.sellchest = sellchest;
		this.location = location;
		this.item = item;
	}

	public String getLoot() {
		return sellchest;
	}

	public Location getLocation() {
		return location;
	}

	public ItemStack getItem() {
		return item;
	}
}
