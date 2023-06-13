package dk.banannus.generators.events.custom.events;

import dk.banannus.generators.events.custom.events.utils.CancellableEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;

public class SellStickSellEvent extends CancellableEvent {

	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private Block clickedBlock;
	private Action action;

	public SellStickSellEvent(Player player, Block clickedBlock, Action action) {
		this.player = player;
		this.clickedBlock = clickedBlock;
		this.action = action;
	}

	public Player getPlayer() {
		return player;
	}

	public Location getLocation() {
		return clickedBlock.getLocation();
	}

	public Block getClickedBlock() {
		return clickedBlock;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
