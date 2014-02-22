package pl.redstonefun.rsutils.main;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pl.redstonefun.rsutils.commands.Commands;
import pl.redstonefun.rsutils.listeners.PlayerJoinListener;
import pl.redstonefun.rsutils.listeners.PlayerJumpPadListener;
import pl.redstonefun.rsutils.listeners.PlayerLessListener;
import pl.redstonefun.rsutils.yaml.YAML;

public class Main extends JavaPlugin {
	
	public static Logger logger;
	public HashMap<String, pl.redstonefun.rsutils.api.Command> commandsList = new HashMap<String, pl.redstonefun.rsutils.api.Command>();
	public static Main instance;
	public static String pluginFolder;
	
	@Override
	public void onEnable() {
		pluginFolder = getDataFolder().getAbsolutePath() + File.separator;
		instance = this;
		logger = getLogger();
		PluginManager manager = getServer().getPluginManager();
		YAML.loadConfigs();
		// Dynamiczne ³adowanie komend
		try {
			Commands commands = new Commands();
			commands.registerCommands();
		} catch (Exception e) {
			logger.severe("Problem z zaladowaniem komend! Wylaczam plugin!");
			e.printStackTrace();
			manager.disablePlugin(this);
		}
		//Koniec
		
		manager.registerEvents(new PlayerJumpPadListener(), this);
		manager.registerEvents(new PlayerJoinListener(), this);
		manager.registerEvents(new PlayerLessListener(), this);
		
		logger.info("Plugin zostal zaladowany!");
	}
	
	@Override
	public void onDisable() {
		logger.info("Plugin zostal wylaczony!");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Commands.commands.get(command.getName()).exec(sender, command.getName(), args);
		return true;
	};
}
