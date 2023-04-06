package dk.banannus.generators.events;

import dk.banannus.generators.events.custom.listeners.*;
import dk.banannus.generators.events.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getServer;

public class ImplementEvents {

	public static void initialise(JavaPlugin plugin) {

		// Normale

		getServer().getPluginManager().registerEvents(new BlockPlace(), plugin);
		getServer().getPluginManager().registerEvents(new PlayerInteractLeft(), plugin);
		getServer().getPluginManager().registerEvents(new Join(), plugin);
		getServer().getPluginManager().registerEvents(new Leave(), plugin);
		getServer().getPluginManager().registerEvents(new BlockBreak(), plugin);

		// Custom

		getServer().getPluginManager().registerEvents(new GenPlace(), plugin);
		getServer().getPluginManager().registerEvents(new GenRemove(), plugin);
		getServer().getPluginManager().registerEvents(new GenUpgrade(), plugin);
	}
}