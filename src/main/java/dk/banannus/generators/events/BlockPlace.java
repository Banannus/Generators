package dk.banannus.generators.events;

import dk.banannus.generators.GenData.GensManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class BlockPlace implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();
		if (block.getState() instanceof StainedClay) {
			StainedClay stainedClay = (StainedClay) block.getState().getData();
			if (stainedClay.getColor() == DyeColor.ORANGE) {
				// The placed block is orange stained clay
				// Do something here
			}
		}
	}

	public void isGenBlock(Material material) {

	}

}
