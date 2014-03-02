package pl.redstonefun.rsutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="kill", description="Zabija gracza")
public class CommandKill implements Command {

	@Override
	public int getMin() {
		return 0;
	}

	@Override
	public int getMax() {
		return 1;
	}

	@Override
	public Object[] getSenders() {
		return null;
	}

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		
		if(args.length == 0){
			if(sender instanceof Player){
				((Player)sender).setHealth(0.0D);
			} else {
				sender.sendMessage(ChatColor.RED + "Nie mo¿esz wykonaæ tej komendy!");
			}
		} else {
			if(sender instanceof Player){
				User user = new User((Player)sender);
				if(!user.hasPermission("rsutils.kill")){
					return;
				}
			}
			
			Player player = Bukkit.getPlayer(args[0]);
			if(player != null){
				player.setHealth(0.0D);
			} else {
				sender.sendMessage(Messages.userOffline);
			}
			
		}	
		
	}

}
