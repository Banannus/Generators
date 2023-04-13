package dk.banannus.generators.events.custom.listeners;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.gen.Gen;
import dk.banannus.generators.data.gen.GensManager;
import dk.banannus.generators.data.player.MultiplierManager;
import dk.banannus.generators.data.sellchest.SellChestItem;
import dk.banannus.generators.data.sellchest.SellChestManager;
import dk.banannus.generators.events.custom.events.SellChestOpenEvent;
import dk.banannus.generators.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.*;

import static dk.banannus.generators.Generators.econ;
import static dk.banannus.generators.data.sellchest.SellChestManager.removeSellChestItemsChangeListener;

public class SellChestOpen implements Listener {

	@EventHandler
	public void onSellChestOpen(SellChestOpenEvent e) {

		Set<SellChestItem> sellChestItemSet = SellChestManager.getSellChestItems().get(e.getPlayer().getUniqueId());
		if (sellChestItemSet == null) {
			Bukkit.broadcastMessage("Tom");
			return;
		}

		opretGUI(e.getPlayer());

	}

	public void opretGUI(Player player) {

		Gui gui = Gui.gui()
				.rows(5)
				.title(Component.text(ConfigManager.get("sell-chest.name")[0]))
				.disableAllInteractions()
				.disableItemDrop()
				.disableItemPlace()
				.disableItemSwap()
				.disableItemTake()
				.create();

		SellChestManager.addSellChestItemsChangeListener(new BukkitRunnable() {

			Set<SellChestItem> sellChestItemSet = SellChestManager.getSellChestItems().get(player.getUniqueId());

			public void run() {
				if (!player.getOpenInventory().getTitle().equals(ConfigManager.get("sell-chest.name")[0])) {
					removeSellChestItemsChangeListener(this);
					return;
				}
				gui.getGuiItems().clear();
				int index = 0;
				for (SellChestItem sellChestItem : sellChestItemSet) {
					String key = sellChestItem.getKey();
					double amount = sellChestItem.getAmount();
					Gen gen = GensManager.getGenList().get(key);
					String dropName = gen.getDropName();
					ItemStack itemStack = GensManager.getDrop(key);
					DecimalFormat df = new DecimalFormat("#.##");
					String formatted = df.format(amount);

					GuiItem item = ItemBuilder.from(itemStack).name(Component.text(ConfigManager.get("sell-chest.item-name", "%item%", Chat.colored(dropName), "%amount%", String.valueOf(formatted))[0]))
							.asGuiItem(event -> {
								event.setCancelled(true);
								SellChestManager.removeSellChestItem(player.getUniqueId(), sellChestItem);
								double price = gen.getSalgspris() * amount;
								double multi = MultiplierManager.getPlayerMultiplier(player.getUniqueId()) * price;
								econ.depositPlayer(player, price);
								String multiForm = df.format(multi);
								player.sendMessage(ConfigManager.get("messages.sell-gui-pr-item", "%amount%", Chat.colored(String.valueOf(formatted)), "%money%", Chat.colored(String.valueOf(multiForm))));
								gui.removeItem(event.getSlot());
								gui.update();
							});
					gui.updateItem(index, item);

					index++;
				}
				gui.update();
			}
		});

		gui.open(player);
		SellChestManager.fireSellChestItemsChangedEvent();
	}
}
