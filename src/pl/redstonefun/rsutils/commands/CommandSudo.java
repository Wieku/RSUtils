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

@RSCommand(command="sudo", description="Wskazany gracz wykona komend�")
public class CommandSudo implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{2,-1};
	}

	@Override
	public Sender getSenders() {
		return Sender.ALL;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		
		if(sender instanceof Player){
			if(!RSUtils.getUser((Player)sender).hasPermission("rsutils.sudo")){
				return;
			}
		}
		
		User user = RSUtils.getUser(args.get(0));
		if(user != null){
			user.executeCommand(args.getFT(1, args.length-1, " "));
		} else {
			sender.sendMessage(Messages.userOffline);
		}
		
	}

}
