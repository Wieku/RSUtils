package pl.redstonefun.rsutils.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.calendar.AfkTimer;
import pl.redstonefun.rsutils.commands.Commands;
import pl.redstonefun.rsutils.listeners.Listeners;
import pl.redstonefun.rsutils.listeners.PlayerSignEditingListener;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.mysql.ConnectionGetter;
import pl.redstonefun.rsutils.mysql.MysqlPing;
import pl.redstonefun.rsutils.profiler.Ticks;
import pl.redstonefun.rsutils.user.TabList;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.yaml.YAML;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;


public class RSUtils extends JavaPlugin implements Listener{
	
	public static Logger logger;
	public static RSUtils instance;
	public static String pluginFolder;
	public PluginManager manager;
	public static ProtocolManager pManager;
	private static HashMap<Player, User> users = new HashMap<Player, User>();
	
	public static void broadcast(String message){
		Bukkit.broadcastMessage(message);
	}
	
	@Override
	public void onEnable() {
		pluginFolder = getDataFolder().getAbsolutePath() + File.separator;
		instance = this;
		logger = getLogger();
		pManager = ProtocolLibrary.getProtocolManager();
		manager = getServer().getPluginManager();
		
		YAML.loadConfigs();
		
		try {
			new Commands().registerCommands();
			new Listeners().register();
		} catch (Exception e) {
			logger.severe("Problem z zaladowaniem komend/listenerów! Wylaczam plugin!");
			e.printStackTrace();
			manager.disablePlugin(this);
		}
		
		PlayerSignEditingListener sl = new PlayerSignEditingListener(this);

		manager.registerEvents(sl, this);
		manager.registerEvents(this, this);
		pManager.addPacketListener(sl);
		
		ConnectionGetter.start();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TabList(), 0, 40);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Ticks(), 0, 1);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new AfkTimer(), 0, 20);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new MysqlPing(), 0, 200);
		
		//Bukkit.getScheduler().runTaskLater(this, new BackupWorlds(), 100);
		
		for(Player player : getServer().getOnlinePlayers()){
			users.put(player, new User(player));
			users.get(player).login();
		}
		
		logger.info("Plugin zostal zaladowany!");
	}
	
	@Override
	public void onDisable() {
		ConnectionGetter.stop();
		logger.info("Plugin zostal wylaczony!");
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		users.put(e.getPlayer(), new User(e.getPlayer()));
		users.get(e.getPlayer()).login();
		System.out.println("fgffff");
		System.out.println(users);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		System.out.println("gvgggggc " + users);
		users.get(e.getPlayer()).left();
		users.remove(e.getPlayer());
	}
	
	public static User[] getUsers(){
		return users.values().toArray(new User[0]);
	}
	
	public static User[] getVisibleUsers(){
		List<User> use = new ArrayList<User>();
		for(User us : users.values()){
			if(!us.isVanished()){
				use.add(us);
			}
		}
		return use.toArray(new User[0]);	
	}
	
	public static User[] sortByRank(User[] array){
		
		Arrays.sort(array, new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				Integer rank = o1.getRank();
				Integer rank1 = o2.getRank();
				return rank.compareTo(rank1);
			}
		});
		
		return array;
	}
	
	public static User getUser(Player player){
		return users.get(player);
	}
	
	public static User getUser(String name){
		Player player = Bukkit.getPlayer(name);
		if(player != null){
			return getUser(player);
		}
		return null;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		pl.redstonefun.rsutils.api.Command comm = Commands.commands.get(command.getName());
		if(comm != null){
			if(comm.getSenders().can(sender)){
				int[] i = comm.getMinMax();
				if(i[0] > args.length || ((i[1] != -1) && args.length > i[1])){
					sender.sendMessage(Messages.notEnoughArguments);
				} else {
					comm.exec(sender, command.getName(), new Arguments(args));	
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Nie możesz wykonać tej komendy");	
			}
		} else {
			sender.sendMessage("Nieznana komenda. Wpisz /help by zobaczyć pomoc");
		}
		return true;
	}
	
}
