package pl.redstonefun.rsutils.main;

import java.net.URL;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pl.redstonefun.rsutils.commands.CommandsGetter;

import com.impetus.annovention.ClasspathDiscoverer;
import com.impetus.annovention.Discoverer;

public class Main extends JavaPlugin {
	public static Logger logger;
	
	public HashMap<String, pl.redstonefun.rsutils.api.Command> commandsList = new HashMap<String, pl.redstonefun.rsutils.api.Command>();

	public static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		logger = getLogger();
		PluginManager manager = getServer().getPluginManager();
		Discoverer discoverer = new ClasspathDiscoverer();
		try {
			discoverer.addAnnotationListener(new CommandsGetter());
			discoverer.discover(true, true, true, true, true);
			for(URL c : discoverer.findResources()){
				logger.info(c.getFile());
			}
		} catch (Exception e) {
			logger.severe("Problem z zaladowaniem komend! Wylaczam plugin!");	
			e.printStackTrace();
			manager.disablePlugin(this);
		}
		
		logger.info("Plugin zostal zaladowany!");
	}
	
	@Override
	public void onDisable() {
		logger.info("Plugin zostal wylaczony!");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		commandsList.get(command).exec(sender, command.getName(), args);
		return true;
	};
}
