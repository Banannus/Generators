package dk.banannus.generators.events;

import dk.banannus.generators.Data.GensManager;
import dk.banannus.generators.Data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;


public class BlockPlace implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {

		short item = e.getItemInHand().getDurability();
		ItemStack itemStack = new ItemStack(e.getBlock().getType(), 1, item);

		if(GensManager.getGenBlocks().containsKey(itemStack)) {
			String key = GensManager.getGenBlocks().get(itemStack);
			Location location = e.getBlock().getLocation();
			UUID uuid = e.getPlayer().getUniqueId();
			PlayerData.saveGen(key, location, uuid);
		}
	}
}
