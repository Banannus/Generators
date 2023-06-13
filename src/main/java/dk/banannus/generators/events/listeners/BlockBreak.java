package dk.banannus.generators.events.listeners;

import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.gen.GensManager;
import dk.banannus.generators.data.player.PlayerDataManager;
import dk.banannus.generators.data.sellchest.SellChestManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {


	private final PlayerDataManager playerDataManager;

	public BlockBreak() {
		this.playerDataManager = new PlayerDataManager();
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {

		Material clickedBlockMaterial = e.getBlock().getType();

		if(clickedBlockMaterial.equals(Material.ENDER_CHEST)) {
			if(SellChestManager.getAllLocations().contains(e.getBlock().getLocation())) {
				ConfigManager.send(e.getPlayer(), "messages.remove-sellchest-block");
				e.setCancelled(true);
				return;
			}
		}


		if(GensManager.isGenNotBlock(clickedBlockMaterial)) {
			return;
		}

		Bukkit.broadcastMessage("here");

		Location blockLoc = e.getBlock().getLocation();

		Bukkit.broadcastMessage("here2");

		if(playerDataManager.getAllLocations().contains(blockLoc)) {
			Bukkit.broadcastMessage("here3");
			ConfigManager.send(e.getPlayer(), "messages.remove-gen-block");
			e.setCancelled(true);
		}
	}
}
