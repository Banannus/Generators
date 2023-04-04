package dk.banannus.generators.events;

import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.gen.GensManager;
import dk.banannus.generators.data.player.PlayerData;
import dk.banannus.generators.data.player.PlayerDataManager;
import dk.banannus.generators.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;


public class BlockPlace implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {

		if(!GensManager.getGenNames().contains(Chat.plain(e.getItemInHand().getItemMeta().getDisplayName()))) {
			return;
		}

		short item = e.getItemInHand().getDurability();
		ItemStack itemStack = new ItemStack(e.getBlock().getType(), 1, item);

		if(!GensManager.getGenBlocks().containsKey(itemStack)) {
			return;
		}

		String key = GensManager.getGenBlocks().get(itemStack);
		Location location = e.getBlock().getLocation();
		UUID uuid = e.getPlayer().getUniqueId();
		PlayerDataManager playerDataManager = new PlayerDataManager();
		playerDataManager.saveGen(key, location, uuid);
		ConfigManager.send(e.getPlayer(), "messages.place-gen");
	}
}
