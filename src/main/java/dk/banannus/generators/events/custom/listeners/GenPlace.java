package dk.banannus.generators.events.custom.listeners;

import ch.njol.skript.SkriptEventHandler;
import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.gen.GensManager;
import dk.banannus.generators.data.player.PlayerDataManager;
import dk.banannus.generators.data.player.SlotsManager;
import dk.banannus.generators.events.custom.events.GenPlaceEvent;
import dk.banannus.generators.events.custom.events.utils.CancellableEvent;
import dk.banannus.generators.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GenPlace implements Listener {

	@EventHandler (priority =  EventPriority.HIGHEST)

	public void onGenPlace(GenPlaceEvent e) {

		if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == null || !GensManager.getGenNames().contains(Chat.plain(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()))) return;

		if(e.isCancelled()) {
			e.setCancelled(true);
			return;
		}

		//DOUBLE CHECK

		short item = e.getBlock().getData();
		ItemStack itemStack = new ItemStack(e.getBlock().getType(), 1, item);

		if(!GensManager.getGenBlockList().containsKey(itemStack)) {
			return;
		}

		UUID uuid = e.getPlayer().getUniqueId();

		if(PlayerDataManager.getAmountOfPlacedGens(uuid) >= SlotsManager.getSlots(uuid)) {
			e.setCancelled(true);
			ConfigManager.send(e.getPlayer(), "messages.max-gens");
			return;
		}

		String key = GensManager.getGenBlockList().get(itemStack);
		Location location = e.getBlock().getLocation();
		PlayerDataManager playerDataManager = new PlayerDataManager();
		playerDataManager.saveGen(key, location, uuid);
		ConfigManager.send(e.getPlayer(), "messages.place-gen");

	}
}
