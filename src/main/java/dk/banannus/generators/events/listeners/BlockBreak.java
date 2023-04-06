package dk.banannus.generators.events.listeners;

import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.gen.GensManager;
import dk.banannus.generators.data.player.PlayerDataManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
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

		if(GensManager.isGenNotBlock(clickedBlockMaterial)) {
			return;
		}

		Location blockLoc = e.getBlock().getLocation();

		if(playerDataManager.getAllLocations().contains(blockLoc)) {
			ConfigManager.send(e.getPlayer(), "messages.remove-gen-block");
			e.setCancelled(true);
		}
	}
}
