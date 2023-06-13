package dk.banannus.generators.events;

import dk.banannus.generators.events.custom.listeners.*;
import dk.banannus.generators.events.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getServer;

public class ImplementEvents {

	public static void initialise(JavaPlugin plugin) {

		// Normale

		getServer().getPluginManager().registerEvents(new BlockPlace(), plugin);
		getServer().getPluginManager().registerEvents(new PlayerInteract(), plugin);
		getServer().getPluginManager().registerEvents(new Join(), plugin);
		getServer().getPluginManager().registerEvents(new Leave(), plugin);
		getServer().getPluginManager().registerEvents(new BlockBreak(), plugin);

		// Custom

		getServer().getPluginManager().registerEvents(new GenPlace(), plugin);
		getServer().getPluginManager().registerEvents(new GenRemove(), plugin);
		getServer().getPluginManager().registerEvents(new GenUpgrade(), plugin);
		getServer().getPluginManager().registerEvents(new SellChestPlace(), plugin);
		getServer().getPluginManager().registerEvents(new SellChestOpen(), plugin);
		getServer().getPluginManager().registerEvents(new SellStickSell(), plugin);
		getServer().getPluginManager().registerEvents(new SellChestRemove(), plugin);
	}
}
