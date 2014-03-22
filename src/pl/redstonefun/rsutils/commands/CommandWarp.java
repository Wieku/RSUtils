package pl.redstonefun.rsutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.warp.Warp;
import pl.redstonefun.rsutils.yaml.YAML;

@RSCommand(command="warp", description="Teleportuje do wybranej lokacji")
public class CommandWarp implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{0,2};
	}

	@Override
	public Sender getSenders() {
		return Sender.PLAYER;
	}
	
	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		
		
		User user = RSUtils.getUser((Player)sender);
		if(args.length == 0){
			user.executeCommand("warplist");
			return;
		}

		if(YAML.isSet(YAML.type.WARPS, args.get(0))){
			
			Warp warp = new Warp(args.get(0));
			
			if(args.length == 1){
				if(user.hasPermission("rsutils.warp.teleport")){
					user.teleport(warp);
				}
			} else if (args.length == 2){
				if(user.hasPermission("rsutils.warp.someone")){
					Player target = Bukkit.getPlayer(args.get(1));
					if(target != null){
						User user2 = RSUtils.getUser(target);
						user2.teleport(warp);
					} else {
						user.sendMessage(Messages.userOffline.replace("%user", args.get(1)));
					}
				}
			}
		} else {
			user.sendMessage(Messages.warpNotFound);
		}
		
	}

}
