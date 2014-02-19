package pl.redstonefun.rsutils.main;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pl.redstonefun.rsutils.commands.Commands;

public class Main extends JavaPlugin {
	
	public static Logger logger;
	public HashMap<String, pl.redstonefun.rsutils.api.Command> commandsList = new HashMap<String, pl.redstonefun.rsutils.api.Command>();
	public static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		logger = getLogger();
		PluginManager manager = getServer().getPluginManager();
		
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
