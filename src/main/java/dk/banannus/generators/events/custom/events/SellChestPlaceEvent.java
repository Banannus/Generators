package dk.banannus.generators.events.custom.events;

import dk.banannus.generators.events.custom.events.utils.CancellableEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class SellChestPlaceEvent extends CancellableEvent {

	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private Block block;


	public SellChestPlaceEvent(Player player, Block block) {
		this.player = player;
		this.block = block;
	}

	public Player getPlayer() {
		return player;
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
