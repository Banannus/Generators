package dk.banannus.generators.events.listeners;

import dk.banannus.generators.events.custom.events.GenRemoveEvent;
import dk.banannus.generators.events.custom.events.GenUpgradeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


public class PlayerInteract implements Listener {


	@EventHandler

	public void onPlayerInteractLeft(PlayerInteractEvent e) {
		GenRemoveEvent genRemoveEvent = new GenRemoveEvent(e.getPlayer(), e.getClickedBlock(), e.getAction());
		Bukkit.getPluginManager().callEvent(genRemoveEvent);
		GenUpgradeEvent genUpgradeEvent = new GenUpgradeEvent(e.getPlayer(), e.getClickedBlock(), e.getAction());
		Bukkit.getPluginManager().callEvent(genUpgradeEvent);
	}
}
