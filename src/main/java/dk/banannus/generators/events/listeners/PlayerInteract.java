package dk.banannus.generators.events.listeners;

import com.avaje.ebean.validation.NotNull;
import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.sellchest.SellChestManager;
import dk.banannus.generators.events.custom.events.GenRemoveEvent;
import dk.banannus.generators.events.custom.events.GenUpgradeEvent;
import dk.banannus.generators.events.custom.events.SellChestOpenEvent;
import dk.banannus.generators.events.custom.events.SellStickSellEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;


public class PlayerInteract implements Listener {


	@EventHandler

	public void onPlayerInteractLeft(PlayerInteractEvent e) {


		if(e.getPlayer().isSneaking()) {
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				GenUpgradeEvent genUpgradeEvent = new GenUpgradeEvent(e.getPlayer(), e.getClickedBlock(), e.getAction());
				Bukkit.getPluginManager().callEvent(genUpgradeEvent);
			} else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
				GenRemoveEvent genRemoveEvent = new GenRemoveEvent(e.getPlayer(), e.getClickedBlock(), e.getAction());
				Bukkit.getPluginManager().callEvent(genRemoveEvent);
			}
		}

		if(e.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {
			UUID uuid = e.getPlayer().getUniqueId();

			if (!SellChestManager.getAllLocations().contains(e.getClickedBlock().getLocation())) {
				return;
			}

			e.setCancelled(true);

			if (!e.getClickedBlock().getLocation().equals(SellChestManager.getSellChestPlayerLocationList().get(uuid))) {
				return;
			}

			if (e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getType() == Material.AIR) {
				e.setCancelled(true);
				SellChestOpenEvent sellChestOpenEvent = new SellChestOpenEvent(e.getPlayer(), e.getClickedBlock(), e.getAction());
				Bukkit.getPluginManager().callEvent(sellChestOpenEvent);
			} else {
				if (e.getPlayer().getItemInHand().hasItemMeta() && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ConfigManager.get("numbers.sell-stick-name")[0])) {
					e.setCancelled(true);
					SellStickSellEvent sellStickSellEvent = new SellStickSellEvent(e.getPlayer(), e.getClickedBlock(), e.getAction());
					Bukkit.getPluginManager().callEvent(sellStickSellEvent);
				} else {
					e.setCancelled(true);
					SellChestOpenEvent sellChestOpenEvent = new SellChestOpenEvent(e.getPlayer(), e.getClickedBlock(), e.getAction());
					Bukkit.getPluginManager().callEvent(sellChestOpenEvent);
				}
			}
		} else {
			return;
		}
	}
}
