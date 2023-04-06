package dk.banannus.generators.events.custom.events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class GenPlaceEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private Block block;
	private boolean isCancelled;
	private boolean blockPlaceAllowed = true;

	public GenPlaceEvent(Player player, Block block) {
		this.player = player;
		this.block = block;
		this.isCancelled = false;
	}

	public Player getPlayer() {
		return player;
	}

	public Location getLocation() {
		return block.getLocation();
	}

	public Block getBlock() {
		return block;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return this.isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public boolean isBlockPlaceAllowed() {
		return blockPlaceAllowed;
	}

	public void setBlockPlaceAllowed(boolean blockPlaceAllowed) {
		this.blockPlaceAllowed = blockPlaceAllowed;
	}

}
