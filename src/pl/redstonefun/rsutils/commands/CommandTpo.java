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

@RSCommand(command="tpo", description="Teleportuje do gracza, omijając jego niewidzialność")
public class CommandTpo implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{1,2};
	}

	@Override
	public Sender getSenders() {
		return Sender.PLAYER;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		
		if(args.length == 1){
			User user = RSUtils.getUser((Player)sender);
			if(user.hasPermission("rsutils.tp.tp.tpo")){
				User target = RSUtils.getUser(args.get(0));
				
				if(target != null){
					user.teleport(target);
				} else {
					sender.sendMessage(Messages.userOffline);
				}
			}
		} else {
			if(!RSUtils.getUser((Player)sender).hasPermission("rsutils.tp.someone.tpo")){
				return;
			}
			
			User target = RSUtils.getUser(args.get(0));
			User target2 = RSUtils.getUser(args.get(1));
			
			if(target != null && target2 != null){
				target.teleport(target2);
			} else {
				sender.sendMessage(Messages.userOffline);
			}
			
		}
		
	}

}
