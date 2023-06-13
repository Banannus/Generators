package dk.banannus.generators;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.file.LoadPlayerFiles;
import dk.banannus.generators.data.gen.GensManager;
import dk.banannus.generators.commands.GeneratorsMainCommand;
import dk.banannus.generators.events.*;
import dk.banannus.generators.utils.Config;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Generators extends JavaPlugin {

    public static Generators instance;
    public static Config gens, config;
    public static FileConfiguration gensYML, configYML;
    public static ConfigManager configManager;
    public static Economy econ = null;
    public static LoadPlayerFiles loadPlayerFiles;
    SkriptAddon addon;

    // TODO:
    //  - Add Args[1] checks
    //  - Add Upgrade droprate
    //  - Upgrade menu
    //  - Admin Commands
    //  - Change EVERY DATA THING TO OBJECT SAVING

    @Override
    public void onEnable() {

        instance = this;

        if(Bukkit.getServer().getPluginManager().getPlugin("Skript") != null){
            addon = Skript.registerAddon(this);
            try {
                addon.loadClasses("dk.banannus.generators", "elements");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Generators - Skript failed to load");
        }



        // Commands

        getCommand("gen").setExecutor(new GeneratorsMainCommand());
      //  getCommand("slots").setExecutor(new GeneratorsMainCommand());
       // getCommand("multi").setExecutor(new GeneratorsMainCommand());
       // getCommand("upgrade").setExecutor(new GeneratorsMainCommand());


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
