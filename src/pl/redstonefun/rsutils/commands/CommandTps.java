package pl.redstonefun.rsutils.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.profiler.Ticks;

@RSCommand(command="tps", description="Wyświetla ilość ticków na sekundę.", aliases={"ticks"})
public class CommandTps implements Command {

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
		
	}

}
