package pl.redstonefun.rsutils.commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.main.Main;

import com.impetus.annovention.listener.ClassAnnotationDiscoveryListener;

public class CommandsGetter implements ClassAnnotationDiscoveryListener {
	
	@Override
	public String[] supportedAnnotations() {
		return new String[]{RSCommand.class.getName()};
	}

	@Override
	public void discovered(String clazz, String annotation) {
		try {
			Class<?> maybe = Class.forName(clazz);
			if(Arrays.asList(maybe.getInterfaces()).contains(Command.class)){
				RSCommand desc = (RSCommand) maybe.getAnnotation(RSCommand.class);

				PluginCommand command = getCommand(desc.command(), Main.instance);
				
				String[] aliases = desc.aliases();
				if(aliases != null){
					command.setAliases(Arrays.asList(aliases));	
				}
				
				command.setDescription(desc.description());
				
				getMap().register(Main.instance.getDescription().getName(), command);
				
				Main.instance.commandsList.put(desc.command(), (Command)maybe.newInstance());
			} else {
				Main.logger.severe("Nieprawid³owa sk³adnia klasy: " + maybe.getName());
			}
		} catch (Exception e) {
			Main.logger.severe("Problem z wczytaniem komendy!");
			e.printStackTrace();
		}
	}
	
	public PluginCommand getCommand(String name, Plugin plugin) throws Exception{
			Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			c.setAccessible(true);
			return c.newInstance(name, plugin);
	}
	
	public CommandMap getMap() throws Exception{
		Field field = SimplePluginManager.class.getDeclaredField("commandMap");
        field.setAccessible(true);
        return (CommandMap)(field.get(Main.instance.getServer().getPluginManager()));
	}
	
	
}
