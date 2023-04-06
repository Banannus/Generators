package dk.banannus.generators.events.custom.listeners;

import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.gen.GensManager;
import dk.banannus.generators.data.player.PlayerDataManager;
import dk.banannus.generators.events.custom.events.GenUpgradeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class GenUpgrade implements Listener {

	private final PlayerDataManager playerDataManager;

	public GenUpgrade() {
		this.playerDataManager = new PlayerDataManager();
	}

	@EventHandler
	public void onGenUpgrade(GenUpgradeEvent e) {

		Player player = e.getPlayer();

		if(!player.isSneaking()) {
			return;
		}

		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if(e.getPlayer().getItemInHand().getType() != Material.AIR) {
				return;
			}

			Material clickedBlockMaterial = e.getClickedBlock().getType();

			if(GensManager.isGenNotBlock(clickedBlockMaterial)) {
				return;
			}



			Location blockLoc = e.getClickedBlock().getLocation();
			UUID uuid = e.getPlayer().getUniqueId();

			HashMap<Location, String> locations = playerDataManager.getAllPlayerLocations(uuid);

			if(!locations.containsKey(blockLoc)) {
				return;
			}

			String key = locations.get(blockLoc);

			if(maxGens(Integer.parseInt(key))) {
				ConfigManager.send(player, "messages.max-upgrade");
				return;
			}

			int keyInt = Integer.parseInt(key) + 1;
			String nextKey = String.valueOf(keyInt);

			playerDataManager.removeGen(blockLoc, uuid);
			playerDataManager.saveGen(nextKey, blockLoc, uuid);

			HashMap<String, ItemStack> materials = GensManager.ReverseGenBlocksList();
			ItemStack itemStack = materials.get(nextKey);

			e.getClickedBlock().setType(itemStack.getType());
			e.getClickedBlock().setData(itemStack.getData().getData());

		}
	}


	private boolean maxGens(int checkKey) {
		Set<String> allKeys = GensManager.getGenList().keySet();
		int maxKey = allKeys.stream().mapToInt(Integer::parseInt).max().orElse(0);
		return checkKey >= maxKey;
	}

}
