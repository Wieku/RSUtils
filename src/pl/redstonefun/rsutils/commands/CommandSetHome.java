package pl.redstonefun.rsutils.commands;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.mysql.HomeSQL;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.warp.Warp;

@RSCommand(command="sethome",description="Ustawia ...")
public class CommandSetHome implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{1,1};
	}

	@Override
	public Sender getSenders() {
		return Sender.PLAYER;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
	
		User user = RSUtils.getUser((Player)sender);
		Warp warp = new Warp(args.get(0).toLowerCase(), user.getLocation());
		
		if(user.hasPermission("rsutils.home.set")){
			
			if(user.hasPermissionSilent("rsutils.home.set.amount.unlimited")){
				
				create(user, warp);
				
			} else {
				HashMap<String, Warp> homes = HomeSQL.getHomes(user.getName().toLowerCase());

				for(int h=1;h<=1000;h++){
					if(user.hasPermissionSilent("rsutils.home.set.amount."+h)){
												
						if(homes.size() < h ||	homes.containsKey(warp.getName())){
							create(user, warp);
							return;
						} else {
							user.sendMessage("&4 Masz już zbyt wiele home'ów");
						}
					}
				}
			}
		}	
	}
	
	public void create(User user, Warp warp){
		
		if(HomeSQL.replace(user.getName().toLowerCase(), warp))
			user.sendMessage("&aStworzono home'a");
		else
			user.sendMessage("&4Problem ze stworzeniem home'a");
	}
	
}
