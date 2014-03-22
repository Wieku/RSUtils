package pl.redstonefun.rsutils.commands;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;

@RSCommand(command="seen")
public class CommandSeen implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{1,1};
	}

	@Override
	public Sender getSenders() {
		return Sender.ALL;
	}
	
	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		
		if(sender instanceof Player){
			if(!RSUtils.getUser((Player)sender).hasPermission("rsutils.seen")){
				return;
			}
		}
		
		String status = "";
		OfflinePlayer player = Bukkit.getOfflinePlayer(args.get(0));
		if(player.hasPlayedBefore()){
			if(player.isOnline()){
				status = ChatColor.GREEN + "Dostępny";
			} else {
				long last = new Date().getTime() - player.getLastPlayed();
				last /= 1000;
				long sec = (last) % 60;
				long min = (last / 60) % 60;
				long hour = (last / (60*60)) % 24;
				long day = (last / (60*60*24));
				status = ChatColor.RED + "Niedostępny od: " + day + " dni, "+ hour + " godzin, "+ min + " minut, "+ sec + " sekund";
			}
		} else {
			status = ChatColor.GRAY + "Nigdy nie grał";
		}
		sender.sendMessage(ChatColor.GREEN + "Gracz: " + player.getName() + ", Status: " + status);
	}

}
