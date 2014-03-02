package pl.redstonefun.rsutils.commands;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="seen")
public class CommandSeen implements Command {

	@Override
	public int getMin() {
		return 1;
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
		
		if(sender instanceof Player){
			if(!new User((Player)sender).hasPermission("rsutils.seen")){
				return;
			}
		}
		
		String status = "";
		OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
		if(player.hasPlayedBefore()){
			if(player.isOnline()){
				status = ChatColor.GREEN + "Dostêpny";
			} else {
				long last = new Date().getTime() - player.getLastPlayed();
				last /= 1000;
				long sec = (last) % 60;
				long min = (last / 60) % 60;
				long hour = (last / (60*60)) % 24;
				long day = (last / (60*60*24));
				status = ChatColor.RED + "Niedostêpny od: " + day + " dni, "+ hour + " godzin, "+ min + " minut, "+ sec + " sekund";
			}
		} else {
			status = ChatColor.GRAY + "Nigdy nie gra³";
		}
		sender.sendMessage(ChatColor.GREEN + "Gracz: " + player.getName() + ", Status: " + status);
	}

}
