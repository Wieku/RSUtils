package pl.redstonefun.rsutils.commands;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.message.I18n;
import pl.redstonefun.rsutils.mysql.HomeSQL;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.warp.Warp;

@RSCommand(command="home")
public class CommandHome implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{0,1};
	}

	@Override
	public Sender getSenders() {
		return Sender.PLAYER;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		User user = RSUtils.getUser((Player)sender);
		if(user.hasPermission("rsutils.home.tp")){
			if(args.length == 0){
				HashMap<String, Warp> homes = HomeSQL.getHomes(user.getName().toLowerCase());
				
				String list = "";
				for(String name : homes.keySet()){
					list = list + ChatColor.GOLD + ChatColor.BOLD + name + ChatColor.RESET + ChatColor.GRAY + ", ";
				}

				user.sendMessage("&aLista home'ów");
				user.sendMessage(list);

			} else {				
				HashMap<String, Warp> homes;
					
				String homeName = "";
					
				if(args.get(0).contains(":")){
					if(user.hasPermission("rsutils.home.tp.someone")){
						String[] split = args.get(0).split(":");
						if(split.length > 2){
							user.sendMessage(I18n.INCORRECTARG.get());
						}
						homes = HomeSQL.getHomes(split[0]);
						homeName = split[1];
					} else {
						return;
					}
				} else {
					homes = HomeSQL.getHomes(user.getName().toLowerCase());
					homeName = args.get(0);
				}
				homeName = homeName.toLowerCase();

				if(!homes.containsKey(homeName)){
					user.sendMessage("&4Home nie istnieje");
					return;
				}

				user.teleport(homes.get(homeName));
			}	
		}
	}

}
