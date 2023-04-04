package dk.banannus.generators.events;

import dk.banannus.generators.Data.GensManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

import static dk.banannus.generators.Data.GensManager.genValues;
import static dk.banannus.generators.Data.PlayerData.getAllLocations;
import static dk.banannus.generators.Data.PlayerData.removeGen;

public class PlayerInteractLeft implements Listener {

	@EventHandler
	public void onPlayerInteractLeft(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			Location blockLoc = e.getClickedBlock().getLocation();
			UUID uuid = e.getPlayer().getUniqueId();

			HashMap<Location, String> locations = getAllLocations(uuid);
			if(locations.containsKey(blockLoc)) {
				e.setCancelled(true);
				removeGen(blockLoc, uuid);
				String block = String.valueOf(e.getClickedBlock().getType());

				if (e.getClickedBlock().getData() > 0) {
					block = block + ":" + e.getClickedBlock().getData();
				}
				String key = GensManager.getKeyFromBlock(block);
				ItemStack item = new ItemStack(e.getClickedBlock().getType(), 1, e.getClickedBlock().getData());
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setDisplayName(genValues.get(key).getName());
				item.setItemMeta(itemMeta);
				Player player = e.getPlayer();
				player.getInventory().addItem(item);

				e.getClickedBlock().setType(Material.AIR);
			}
		}
	}
}
