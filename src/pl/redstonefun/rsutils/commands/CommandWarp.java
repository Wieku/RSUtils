package pl.redstonefun.rsutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.warp.Warp;
import pl.redstonefun.rsutils.yaml.YAML;

@RSCommand(command="warp", description="Teleportuje do wybranej lokacji")
public class CommandWarp implements Command {

	@Override
	public int getMin() {
		return 0;
	}

	@Override
	public int getMax() {
		return 2;
	}

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		User user = new User((Player)sender);
		
		if(args.length == 0){
			user.executeCommand("warplist");
			return;
		}

		if(YAML.isSet(YAML.type.WARPS, args[0])){
			
			Warp warp = new Warp(args[0]);
			
			if(args.length == 1){
				if(user.hasPermission("rsutils.warp.teleport")){
					user.teleport(warp);
				}
			} else if (args.length == 2){
				if(user.hasPermission("rsutils.warp.someone")){
					User target = new User(Bukkit.getPlayer(args[1]));
					if(target.isOnline()){
						target.teleport(warp);
					} else {
						user.sendMessage(Messages.userOffline.replace("%user", args[1]));
					}
				}
			}
			
			
		} else {
			user.sendMessage(Messages.warpNotFound);
		}
		
	}

}
