package dk.banannus.generators.events.listeners;

import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.sellchest.SellChestManager;
import dk.banannus.generators.events.custom.events.*;
import dk.banannus.generators.events.custom.events.utils.CancellableEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;


public class PlayerInteract implements Listener {


	@EventHandler (priority =  EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent e) {


		if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.ENDER_CHEST) {

			UUID uuid = e.getPlayer().getUniqueId();

			if (!SellChestManager.getAllLocations().contains(e.getClickedBlock().getLocation())) return;

			if (!e.getClickedBlock().getLocation().equals(SellChestManager.getSellChestPlayerLocationList().get(uuid))) {
				ConfigManager.send(e.getPlayer(), "messages.sell-chest-not-yours");
				e.setCancelled(true);
				return;
			}

			if(e.getPlayer().isSneaking()) {
				CancellableEvent sellChestBreakEvent = new SellChestRemoveEvent(e.getPlayer(), e.getClickedBlock(), e.getAction());
				sellChestBreakEvent.call();
				e.setCancelled(sellChestBreakEvent.isCancelled());
			}

			if (e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getType() == Material.AIR) {
				if(!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
				CancellableEvent sellChestOpenEvent = new SellChestOpenEvent(e.getPlayer(), e.getClickedBlock(), e.getAction());
				sellChestOpenEvent.call();
				e.setCancelled(sellChestOpenEvent.isCancelled());
				e.setCancelled(true);
			} else {
				if (e.getPlayer().getItemInHand().hasItemMeta() && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ConfigManager.get("numbers.sell-stick-name")[0])) {
					CancellableEvent sellStickSellEvent = new SellStickSellEvent(e.getPlayer(), e.getClickedBlock(), e.getAction());
					sellStickSellEvent.call();
					e.setCancelled(sellStickSellEvent.isCancelled());
					e.setCancelled(true);
				} else {
					if(!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
					CancellableEvent sellChestOpenEvent = new SellChestOpenEvent(e.getPlayer(), e.getClickedBlock(), e.getAction());
					sellChestOpenEvent.call();
					e.setCancelled(sellChestOpenEvent.isCancelled());
					e.setCancelled(true);
				}
			}
		}

		if (e.getPlayer().isSneaking()) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				CancellableEvent genUpgradeEvent = new GenUpgradeEvent(e.getPlayer(), e.getClickedBlock(), e.getAction());
				genUpgradeEvent.call();
				e.setCancelled(genUpgradeEvent.isCancelled());
			} else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
				CancellableEvent genRemoveEvent = new GenRemoveEvent(e.getPlayer(), e.getClickedBlock(), e.getAction());
				genRemoveEvent.call();
				e.setCancelled(genRemoveEvent.isCancelled());
			}
		}
	}
}
