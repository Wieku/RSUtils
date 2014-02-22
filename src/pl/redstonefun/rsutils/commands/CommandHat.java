package pl.redstonefun.rsutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command = "hat", description = "Zmienia twoj¹ czapkê")
public class CommandHat implements Command {

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		
		if(sender instanceof Player){
			User user = new User((Player) sender);
			if(user.hasPermission("tsutils.hat")){
				
			}
		} else {
			sender.sendMessage(Messages.hasNoPermission);
		}

	}

}
