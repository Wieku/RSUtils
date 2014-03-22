package pl.redstonefun.rsutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.message.I18n;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="kickall", description="Wyrzuca graczy z serwera")
public class CommandKickAll implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{0,-1};
	}

	@Override
	public Sender getSenders() {
		return new Sender(Sender.PLAYER, Sender.CONSOLE);
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {			
		User user = null;
		if(sender instanceof Player){
			user = RSUtils.getUser((Player)sender);
			if(!user.hasPermission("rsutils.kick.all")){
				return;
			}
		}			
		String reason = (args.length > 0 ? args.getFT(1, args.length-1, " "):"Admin ma zawsze rację!");	
		
		for(User target : RSUtils.getUsers()){
			if(sender instanceof Player){
				if(!target.equals(user)){
					target.kick(I18n.UKICK.getE().write(0, reason).get());
				}
			} else {
				target.kick(I18n.UKICK.getE().write(0, reason).get());
			}
		}		
	}
}