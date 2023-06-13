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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import static dk.banannus.generators.Generators.econ;

public class GenUpgrade implements Listener {

	private final PlayerDataManager playerDataManager;

	public GenUpgrade() {
		this.playerDataManager = new PlayerDataManager();
	}

	@EventHandler (priority =  EventPriority.HIGHEST)
	public void onGenUpgrade(GenUpgradeEvent e) {

		if(e.isCancelled()) {
			e.setCancelled(true);
			return;
		}

		Player player = e.getPlayer();

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

		if(econ.getBalance(player) >= GensManager.getGenList().get(key).getUpgradepris()) {
			int keyInt = Integer.parseInt(key) + 1;
			String nextKey = String.valueOf(keyInt);

			econ.withdrawPlayer(player, GensManager.getGenList().get(key).getUpgradepris());

			playerDataManager.removeGen(blockLoc, uuid);
			playerDataManager.saveGen(nextKey, blockLoc, uuid);

			HashMap<String, ItemStack> materials = GensManager.ReverseGenBlocksList();
			ItemStack itemStack = materials.get(nextKey);

			e.getClickedBlock().setType(itemStack.getType());
			e.getClickedBlock().setData(itemStack.getData().getData());

		} else {
			int difference = (int) (GensManager.getGenList().get(key).getUpgradepris() - econ.getBalance(player));
			NumberFormat formatter = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.GERMANY));
			String money_format = formatter.format(difference);
			ConfigManager.send(player, "messages.no-money", "%money%", money_format);
		}
	}


	private boolean maxGens(int checkKey) {
		Set<String> allKeys = GensManager.getGenList().keySet();
		int maxKey = allKeys.stream().mapToInt(Integer::parseInt).max().orElse(0);
		return checkKey >= maxKey;
	}

}
