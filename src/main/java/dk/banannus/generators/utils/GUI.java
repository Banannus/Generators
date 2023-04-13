package dk.banannus.generators.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GUI {

	public static ItemStack createItemGlass(Material material, int GlassColor, String displayName, String... loreString) {
		List<String> lore = new ArrayList<>();
		ItemStack item = new ItemStack(material, 1, (short) GlassColor);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Chat.colored(displayName));

		for (String s : loreString)
			lore.add(Chat.colored(s));

		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

}
