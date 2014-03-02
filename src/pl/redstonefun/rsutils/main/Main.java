package pl.redstonefun.rsutils.main;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import pl.redstonefun.rsutils.commands.Commands;
import pl.redstonefun.rsutils.listeners.Listeners;
import pl.redstonefun.rsutils.listeners.PlayerSignEditingListener;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.yaml.YAML;

public class Main extends JavaPlugin {
	
	public static Logger logger;
	public static Main instance;
	public static String pluginFolder;
	public PluginManager manager;
	public static ProtocolManager pManager;
	
	@Override
	public void onEnable() {
		pluginFolder = getDataFolder().getAbsolutePath() + File.separator;
		instance = this;
		logger = getLogger();
		pManager = ProtocolLibrary.getProtocolManager();
		manager = getServer().getPluginManager();
		
		YAML.loadConfigs();
		
		try {
			Commands commands = new Commands();
			commands.registerCommands();
			new Listeners().register();
		} catch (Exception e) {
			logger.severe("Problem z zaladowaniem komend/listenerów! Wylaczam plugin!");
			e.printStackTrace();
			manager.disablePlugin(this);
		}
		
		PlayerSignEditingListener sl = new PlayerSignEditingListener(this);
		
		manager.registerEvents(sl, this);
		pManager.addPacketListener(sl);
		
		for(Player player : getServer().getOnlinePlayers()){
			new User(player).updateListName();
		}
		
		logger.info("Plugin zostal zaladowany!");
	}
	
	@Override
	public void onDisable() {
		try {
			YAML.saveAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Plugin zostal wylaczony!");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		pl.redstonefun.rsutils.api.Command comm = Commands.commands.get(command.getName());
		if(comm != null){
			if(comm.getSenders() != null){
				for(Object x : comm.getSenders()){
					if(((Class<?>)x).isInstance(sender)){
						execute(comm, sender, command, args);
						return true;
					}
				}
				sender.sendMessage(ChatColor.RED + "Nie mo¿esz wykonaæ tej komendy");
			} else {
				execute(comm, sender, command, args);
			}	
		} else {
			sender.sendMessage("Nieznana komenda. Wpisz /help by zobaczyæ pomoc");
		}
		return true;
	}
	
	public void execute(pl.redstonefun.rsutils.api.Command comm, CommandSender sender, Command command, String[] args){
		if(comm.getMin() > args.length){
			sender.sendMessage(Messages.notEnoughArguments);
			return;
		}
		if((comm.getMax() != -1) && args.length > comm.getMax()){
			sender.sendMessage(Messages.notEnoughArguments);
			return;
		}		
		comm.exec(sender, command.getName(), args);		
	}
	
}
