package dk.banannus.generators.events.custom.listeners;

import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.gen.Gen;
import dk.banannus.generators.data.gen.GensManager;
import dk.banannus.generators.data.player.MultiplierManager;
import dk.banannus.generators.data.sellchest.SellChestItem;
import dk.banannus.generators.data.sellchest.SellChestManager;
import dk.banannus.generators.events.custom.events.SellChestOpenEvent;
import dk.banannus.generators.events.custom.events.SellStickSellEvent;
import dk.banannus.generators.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.DecimalFormat;
import java.util.Set;

import static dk.banannus.generators.Generators.econ;

public class SellStickSell implements Listener {

	@EventHandler
	public void onSellStickSell(SellStickSellEvent e) {
		Player player = e.getPlayer();

		Set<SellChestItem> sellChestItemSet = SellChestManager.getSellChestItems().get(player.getUniqueId());

		double totalAmount = 0;
		double totalPrice = 0;
		for(SellChestItem sellChestItem : sellChestItemSet) {
			String key = sellChestItem.getKey();
			double amount = sellChestItem.getAmount();
			Gen gen = GensManager.getGenList().get(key);
			double price = gen.getSalgspris() * amount;
			double multi = MultiplierManager.getPlayerMultiplier(player.getUniqueId()) * price;
			totalAmount += amount;
			totalPrice += multi;
		}
		SellChestManager.getSellChestItems().get(player.getUniqueId()).clear();

		String multi = ConfigManager.get("numbers.sell-stick-multiplier")[0];

		double lastPrice = totalPrice * Integer.parseInt(multi);
		DecimalFormat df = new DecimalFormat("#.##");
		String formatted = df.format(lastPrice);
		String totalForm = df.format(totalAmount);

		econ.depositPlayer(player , lastPrice);
		player.sendMessage(ConfigManager.get("messages.sell-stick-all-items", "%amount%", Chat.colored(String.valueOf(totalForm)), "%money%", Chat.colored(String.valueOf(formatted))));
	}
}
