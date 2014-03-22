package pl.redstonefun.rsutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.profiler.Ticks;

@RSCommand(command="memory", description="Informacje dot. serwera.", aliases={"mem", "lag"})
public class CommandMemory implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{0,0};
	}

	@Override
	public Sender getSenders() {
		return Sender.ALL;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		if(sender instanceof Player){
			if(!RSUtils.getUser((Player)sender).hasPermission("rsutils.memory")){
				return;
			}
		}
		
		for(World world : Bukkit.getWorlds()){
			sender.sendMessage(ChatColor.GREEN + "Świat \"" + world.getName() + "\", Załadowanych chunków: " + world.getLoadedChunks().length + " Jednostek(Entities): " + world.getEntities().size());
		}
		Runtime r = Runtime.getRuntime();
		sender.sendMessage(ChatColor.GREEN + "Używanej pamięci: " + ((r.totalMemory() - r.freeMemory())/1000000)+"MB");
		sender.sendMessage(ChatColor.GREEN + "Wolnej pamięci: " + (r.freeMemory()/1000000)+"MB");
		sender.sendMessage(ChatColor.GREEN + "Maksymalnej pamięci: " + (r.maxMemory()/1000000)+"MB");
		sender.sendMessage(ChatColor.GREEN + "Całkowitej pamięci: " + (r.totalMemory()/1000000)+"MB");
		
		ChatColor color;
		double ticks = Ticks.getCurrentTicks(true);
		
		if(ticks >= 15){
			color = ChatColor.GREEN;
		} else if(ticks >= 10 && ticks < 15){
			color = ChatColor.GOLD;
		} else {
			color = ChatColor.RED;
		}
		
		sender.sendMessage(ChatColor.GREEN + "[TPS] " + color + ticks);
		ChatColor color1;
		double ticks1 = Ticks.getAverageTicks(true);
		
		if(ticks1 >= 15){
			color1 = ChatColor.GREEN;
		} else if(ticks1 >= 10 && ticks < 15){
			color1 = ChatColor.GOLD;
		} else {
			color1 = ChatColor.RED;
		}
		
		sender.sendMessage(ChatColor.GREEN + "[Śr.TPS] " + color1 + ticks1);
		sender.sendMessage(ChatColor.GREEN + "[Śr. czas ticku] " + Ticks.getAverageTickTime());
		
	}

}
