package dk.banannus.generators.events.custom.events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;

public class GenUpgradeEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private Block clickedBlock;
	private Action action;
	private boolean isCancelled;

	public GenUpgradeEvent(Player player, Block clickedBlock, Action action) {
		this.player = player;
		this.clickedBlock = clickedBlock;
		this.action = action;
		this.isCancelled = false;
	}

	public Player getPlayer() {
		return player;
	}

	public Location getLocation() {
		return clickedBlock.getLocation();
	}

	public Action getAction() {
		return action;
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

	@Override
	public boolean isCancelled() {
		return this.isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
}
