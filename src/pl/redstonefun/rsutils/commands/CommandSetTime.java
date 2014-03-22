package pl.redstonefun.rsutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.message.I18n;

@RSCommand(command="time", description="Zmienia czas na świecie", aliases={"timeset", "tset", "settime"})
public class CommandSetTime implements Command {

	enum Time{
		day(6000),
		night(18000);
		
		int time;
		
		Time(int time){
			this.time = time;
		}
		
		public static Time parse(String name) throws Exception{
			for(Time t : values()){
				if(t.name().equals(name)){
					return t;
				}
			}
			throw new Exception();
		}
	}
	
	
	@Override
	public int[] getMinMax() {
		return new int[]{1,2};
	}

	@Override
	public Sender getSenders() {
		return Sender.ALL;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {

		if(sender instanceof Player){
			if(!RSUtils.getUser((Player)sender).hasPermission("rsutils.time")){
				return;
			}
		}
		
		if(args.length == 1){
			
			if(sender instanceof Player){
				setTime(sender, args.get(0), ((Player)sender).getWorld());
			} else if (sender instanceof BlockCommandSender){
				setTime(sender, args.get(0), ((BlockCommandSender)sender).getBlock().getWorld());
			} else {
				sender.sendMessage(I18n.WRONGARGS.get()); 
			}
			
		} else {
			if(args.get(1).equals("-all")){
				setTime(sender, args.get(0), Bukkit.getWorlds().toArray(new World[0]));
			} else {
				World world = Bukkit.getWorld(args.get(1));
				if(world != null){
					setTime(sender, args.get(0), world);
				} else {
					sender.sendMessage(I18n.WODOESNTEXIST.get());
					return;
				}
			}	
		}
		
		sender.sendMessage(ChatColor.GREEN + "Zmieniono!");
		
	}

	public void setTime(CommandSender sender, String arg, World... worlds){
		
		long time = 0;
		
		try {			
			time = Long.parseLong(arg);	
		} catch(NumberFormatException e){
			try {
				time = Time.parse(arg).time;
			} catch(Exception e1){
				sender.sendMessage(I18n.INCORRECTARG.get());
				return;
			}
		}
		
		for(World world : worlds){
			world.setTime(time);
		}
		
	}
}
