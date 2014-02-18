package pl.redstonefun.rsutils.commands;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.SimplePluginManager;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.main.Main;

import com.impetus.annovention.listener.ClassAnnotationDiscoveryListener;

public class CommandsGetter implements ClassAnnotationDiscoveryListener {

	CommandMap commandMap;
	
	public CommandsGetter() throws Exception{
		Field field = SimplePluginManager.class.getDeclaredField("commandMap");
        field.setAccessible(true);
        commandMap = (CommandMap)(field.get(Main.instance.getServer().getPluginManager()));
	}
	
	@Override
	public String[] supportedAnnotations() {
		Main.logger.info("Annot");
		return new String[]{RSCommand.class.getName()};
	}

	@Override
	public void discovered(String clazz, String annotation) {
		Main.logger.info("Znaleziono " + clazz);
		try {
			Class<?> maybe = Class.forName(clazz);
			if(Arrays.asList(maybe.getInterfaces()).contains(Command.class)){
				RSCommand desc = (RSCommand) maybe.getAnnotation(RSCommand.class);
			String command = desc.command();
			String description = desc.description();
			String[] aliases = desc.aliases();
			PluginCommand comm = Main.instance.getCommand(command);
			comm.setAliases(Arrays.asList(aliases));
			comm.setDescription(description);
			Main.instance.commandsList.put(command, (Command)maybe.newInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
