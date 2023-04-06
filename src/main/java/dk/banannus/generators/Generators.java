package dk.banannus.generators;

import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.gen.GensManager;
import dk.banannus.generators.commands.Test;
import dk.banannus.generators.events.*;
import dk.banannus.generators.utils.Config;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Generators extends JavaPlugin {

    public static Generators instance;
    public static Config gens, config;
    public static FileConfiguration gensYML, configYML;
    public static ConfigManager configManager;
    public static Economy econ = null;


    @Override
    public void onEnable() {

        instance = this;

        // Commands

        getCommand("gen").setExecutor(new Test());

        // Listeners

        ImplementEvents.initialise(this);

        // Gens config

        if (!(new File(this.getDataFolder(), "gens.yml")).exists()) {
            this.saveResource("gens.yml", false);
        }

        gens = new Config(this, null, "gens.yml");
        gensYML = gens.getConfig();

        // Config

        if (!(new File(this.getDataFolder(), "config.yml")).exists()) {
            this.saveResource("config.yml", false);
        }

        config = new Config(this, null, "config.yml");
        configYML = config.getConfig();

        //VAULT // ECON
        if (!setupEconomy() ) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            Bukkit.getLogger().severe(String.format(String.valueOf(getServer().getPluginManager().getPlugin("Vault"))));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupEconomy();


        // Load Stuff
        configManager = new ConfigManager();

        configManager.loadALl();
        GensManager.loadGens();
        GensManager.loadGenBlocks();
    }

    @Override
    public void onDisable() {


    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
