package dk.banannus.generators;

import dk.banannus.generators.commands.Multiplier;
import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.file.LoadPlayerFiles;
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
    public static LoadPlayerFiles loadPlayerFiles;

    // TODO: Add sell-chest break
    // TODO: Add checks til hvis man har en sell-chest/gens/items ved gemning af data (Leave)
    // TODO: Add xp til alting
    // TODO: Add checks til sell-chest tom
    // TODO: FIX THIS SHIT


    @Override
    public void onEnable() {

        instance = this;

        // Commands

        getCommand("gen").setExecutor(new Test());
        getCommand("multiplier").setExecutor(new Multiplier());

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

        // Sellchest locations



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
        loadPlayerFiles = new LoadPlayerFiles();

        loadPlayerFiles.loadPlayerFilesAsync(this);

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

    public static Generators getInstance() {
        return instance;
    }
}
