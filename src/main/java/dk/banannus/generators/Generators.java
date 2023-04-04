package dk.banannus.generators;

import dk.banannus.generators.Data.GensManager;
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
    public static Config gens;
    public static FileConfiguration gensYML;


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

        // Player folder



        // Load gens

        GensManager.loadGens();
    }

    @Override
    public void onDisable() {


    }
}
