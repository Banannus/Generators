package dk.banannus.generators.events.custom.listeners;

import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.gen.GensManager;
import dk.banannus.generators.data.player.PlayerDataManager;
import dk.banannus.generators.data.player.slots.SlotsManager;
import dk.banannus.generators.events.custom.events.GenPlaceEvent;
import dk.banannus.generators.utils.Chat;
import dk.banannus.generators.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GenPlace implements Listener {

	@EventHandler
	public void onGenPlace(GenPlaceEvent e) {

		//DOUBLE CHECK
		if(!GensManager.getGenNames().contains(Chat.plain(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()))) {
			return;
		}

		short item = e.getBlock().getData();
		ItemStack itemStack = new ItemStack(e.getBlock().getType(), 1, item);

		if(!GensManager.getGenBlockList().containsKey(itemStack)) {
			return;
		}

		UUID uuid = e.getPlayer().getUniqueId();

		Bukkit.broadcastMessage(String.valueOf(PlayerDataManager.getAmountOfPlacedGens(uuid)));

		if(PlayerDataManager.getAmountOfPlacedGens(uuid) >= SlotsManager.getSlots(uuid)) {
			e.setCancelled(true);
			e.setBlockPlaceAllowed(false);
			ConfigManager.send(e.getPlayer(), "messages.max-gens");
			return;
		}

		String key = GensManager.getGenBlockList().get(itemStack);
		Location location = e.getLocation();
		PlayerDataManager playerDataManager = new PlayerDataManager();
		playerDataManager.saveGen(key, location, uuid);
		ConfigManager.send(e.getPlayer(), "messages.place-gen");
	}

}
