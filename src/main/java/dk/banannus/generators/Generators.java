package dk.banannus.generators;

import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.gen.GensManager;
import dk.banannus.generators.commands.Test;
import dk.banannus.generators.events.PlayerInteractLeft;
import dk.banannus.generators.events.BlockPlace;
import dk.banannus.generators.events.OnFirstJoin;
import dk.banannus.generators.events.OnLeave;
import dk.banannus.generators.utils.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Generators extends JavaPlugin {

    public static Generators instance;
    public static Config gens, config;
    public static FileConfiguration gensYML, configYML;
    public static ConfigManager configManager;


    @Override
    public void onEnable() {

        instance = this;

        // Commands

        getCommand("gen").setExecutor(new Test());

        // Listeners


        getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractLeft(), this);
        getServer().getPluginManager().registerEvents(new OnFirstJoin(), this);
        getServer().getPluginManager().registerEvents(new OnLeave(), this);

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

        // Load Stuff
        configManager = new ConfigManager();

        configManager.loadALl();
        GensManager.loadGens();
    }

    @Override
    public void onDisable() {


    }
}
