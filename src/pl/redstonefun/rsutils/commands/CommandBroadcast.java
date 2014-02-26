package pl.redstonefun.rsutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="broadcast", description="Wysy³a wiadomoœæ serwerow¹", aliases={"say"})
public class CommandBroadcast implements Command {

	@Override
	public int getMin() {
		return 0;
	}

	@Override
	public int getMax() {
		return -1;
	}

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		
		String message = "";
		for(int i=0;i<args.length;i++){
			message += (i==0?"":" ") + args[i];
		}
		
		if(sender instanceof Player){
			User user = new User((Player)sender);
			if(!user.hasPermission("rsutils.broadcast")){
				return;
			}
		}
			
		Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Powiadomienie] " + sender.getName() + ": " + ChatColor.translateAlternateColorCodes('&', message));
		
		
	}

}
