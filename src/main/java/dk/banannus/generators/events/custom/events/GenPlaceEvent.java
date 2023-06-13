package dk.banannus.generators.events.custom.events;

import dk.banannus.generators.events.custom.events.utils.CancellableEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class GenPlaceEvent extends CancellableEvent {

	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private Block block;

	public GenPlaceEvent(Player player, Block block) {
		this.player = player;
		this.block = block;
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

}
