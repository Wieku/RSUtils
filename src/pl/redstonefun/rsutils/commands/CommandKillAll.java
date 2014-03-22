package pl.redstonefun.rsutils.commands;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="killall", description="Zabija moby w obszarze")
public class CommandKillAll implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{1,1};
	}

	@Override
	public Sender getSenders() {
		return new Sender(Sender.PLAYER, Sender.BLOCK);
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		
		if(sender instanceof Player){
			if(!new User((Player)sender).hasPermission("rsutils.ban")){
				return;
			}
		}
		
		double dist = 0;
		
		try {
			dist = Double.parseDouble(args.get(0));
			if(dist < 0){
				sender.sendMessage(ChatColor.RED + "Liczba musi być większa lub równa 0");
				return;
			}
		} catch(NumberFormatException e){
			sender.sendMessage(ChatColor.RED + "Podaj liczbę!");
			return;
		}
		
		Location loc = null;
		
		if(sender instanceof Player){
			loc = ((Player) sender).getLocation();
		} else if(sender instanceof BlockCommandSender){
			loc = (((BlockCommandSender) sender).getBlock()).getLocation();
		}
		
		int count = 0;
		
		for(LivingEntity ent : loc.getWorld().getLivingEntities()){
			if(!(ent instanceof Player)){
				if(ent.getLocation().distance(loc) <= dist){
					++count;
					ent.remove();
				}
			}
		}
		
		sender.sendMessage(ChatColor.GREEN + "Usunięto " + count + " mobów  w promieniu " + dist + " kratek");
		
	}

}
