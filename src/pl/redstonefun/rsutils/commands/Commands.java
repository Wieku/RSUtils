package pl.redstonefun.rsutils.commands;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.main.Main;


public class Commands {

	public static HashMap<String, Command> commands = new HashMap<String, Command>();
	
	public List<Class<?>> find() throws Exception{
		List<Class<?>> list = new ArrayList<Class<?>>();
		URL filePath = getClass().getProtectionDomain().getCodeSource().getLocation();
		ZipFile file = new ZipFile(new File(filePath.toURI()));
		Enumeration<? extends ZipEntry> entries = file.entries();
		while(entries.hasMoreElements()){
			ZipEntry entry = entries.nextElement();
			String name = entry.getName();
			if(!entry.isDirectory()){
				if(name.split("\\.")[name.split("\\.").length -1].equals("class")){
					name = name.replace(".class", "").replace("/", ".");
					Class<?> forCheck = Class.forName(name);
					for(Annotation v : forCheck.getAnnotations()){	
						if(v instanceof RSCommand){
							if(Arrays.asList(forCheck.getInterfaces()).contains(Command.class)){
								list.add(forCheck);
							} else {
								Main.logger.severe("Nieprawidlowa skladnia klasy: " + name);						
							}
						}
					}
				}
			}
		}			
		file.close();
		return list;
	}
	
	public void registerCommands() throws Exception{
		
		List<Class<?>> list = find();
		
		for(Class<?> classe : list){
			RSCommand annotation = classe.getAnnotation(RSCommand.class);
			PluginCommand command = getCommand(annotation.command(), Main.instance);
					
			String[] aliases = annotation.aliases();
			if(aliases != null){
				command.setAliases(Arrays.asList(aliases));	
			}
					
			command.setDescription(annotation.description());	
			getCommandMap().register(Main.instance.getDescription().getName(), command);
					
			commands.put(annotation.command(), (Command)classe.newInstance());
			Main.logger.info("Zaladowano komende: " + annotation.command());
		}
		
	}
	
	public static PluginCommand getCommand(String name, Plugin plugin) throws Exception{
		Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
		c.setAccessible(true);
		return c.newInstance(name, plugin);
	}

	public static CommandMap getCommandMap() throws Exception{
		Field field = SimplePluginManager.class.getDeclaredField("commandMap");
		field.setAccessible(true);
		return (CommandMap)(field.get(Main.instance.getServer().getPluginManager()));
	}
	
}