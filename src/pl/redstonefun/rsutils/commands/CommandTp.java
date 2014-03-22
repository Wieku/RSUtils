package pl.redstonefun.rsutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="tp", description="Teleportuje ciebie/gracza do gracza")
public class CommandTp implements Command {

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
		
		if(!(sender instanceof Player) && args.length == 1){
			sender.sendMessage(Messages.notEnoughArguments);
			return;
		}
		
		if(args.length == 1){
			User user = RSUtils.getUser((Player)sender);
			if(user.hasPermission("rsutils.tp.tp")){
				User target = RSUtils.getUser(args.get(0));
				
				if(target != null){
					
					if(!(sender instanceof Player) || !target.isVanished()){
						user.teleport(target);
					} else {
						sender.sendMessage(Messages.userOffline);
					}
				} else {
					sender.sendMessage(Messages.userOffline);
				}
			}
		} else {
			if(sender instanceof Player){
				if(!RSUtils.getUser((Player)sender).hasPermission("rsutils.tp.someone")){
					return;
				}
			}
			
			User target = RSUtils.getUser(args.get(0));
			User target2 = RSUtils.getUser(args.get(1));
			
			if(target != null && target2 != null){
				
				if(!(sender instanceof Player) || !target.isVanished() && !target.isVanished()){
					target.teleport(target2);
				} else {
					sender.sendMessage(Messages.userOffline);
				}
				
			} else {
				sender.sendMessage(Messages.userOffline);
			}
			
		}
		
	}

}
