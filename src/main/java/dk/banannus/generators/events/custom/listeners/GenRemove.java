package dk.banannus.generators.events.custom.listeners;

import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.gen.Gen;
import dk.banannus.generators.data.gen.GensManager;;
import dk.banannus.generators.data.player.PlayerDataManager;
import dk.banannus.generators.events.custom.events.GenRemoveEvent;
import dk.banannus.generators.utils.Chat;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

public class GenRemove implements Listener {

	private final PlayerDataManager playerDataManager;

	public GenRemove() {
		this.playerDataManager = new PlayerDataManager();
	}

	@EventHandler (priority =  EventPriority.HIGHEST)
	@SuppressWarnings("deprecation")
	public void onGenRemove(GenRemoveEvent e) {
		if(!e.getPlayer().isSneaking()) {
			return;
		}

		if(e.isCancelled()) {
			e.setCancelled(true);
			return;
		}

		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {

			Material clickedBlockMaterial = e.getClickedBlock().getType();

			if(GensManager.isGenNotBlock(clickedBlockMaterial)) {
				return;
			}

			Location blockLoc = e.getClickedBlock().getLocation();
			UUID uuid = e.getPlayer().getUniqueId();

			HashMap<Location, String> locations = playerDataManager.getAllPlayerLocations(uuid);

			if(locations == null || locations.containsKey(null)) {
				if(playerDataManager.getAllLocations().contains(blockLoc)) {
					ConfigManager.send(e.getPlayer(), "messages.remove-others-gens");
					return;
				}
				return;
			}

			if(!locations.containsKey(blockLoc)) {
				return;
			}

			e.setCancelled(true);
			playerDataManager.removeGen(blockLoc, uuid);

			String key = locations.get(blockLoc);
			ItemStack item = new ItemStack(e.getClickedBlock().getType(), 1, e.getClickedBlock().getData());
			ItemMeta itemMeta = item.getItemMeta();
			HashMap<String, Gen> genValues = GensManager.getGenList();
			itemMeta.setDisplayName(Chat.colored(genValues.get(key).getName()));
			item.setItemMeta(itemMeta);

			Player player = e.getPlayer();
			player.getInventory().addItem(item);
			ConfigManager.send(player, "messages.remove-gen");

			e.getClickedBlock().setType(Material.AIR);
		}
	}
}
