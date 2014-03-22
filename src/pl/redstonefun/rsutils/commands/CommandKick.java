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

@RSCommand(command="kick", description="Wyrzuca gracza z serwera")
public class CommandKick implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{1,-1};
	}

	@Override
	public Sender getSenders() {
		return new Sender(Sender.PLAYER, Sender.CONSOLE);
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
				
		if(sender instanceof Player){
			if(!RSUtils.getUser((Player)sender).hasPermission("rsutils.kick")){
				return;
			}
		}
		
		User target = RSUtils.getUser(args.get(0));
		
		if(target != null){
			String reason = (args.length > 1? args.getFT(1, args.length-1, " "):"Admin ma zawsze rację!");	
			target.kick(I18n.UKICK.getE().write(0, reason).get());
			RSUtils.broadcast(I18n.USKICK.getE().write(0, target.getColoredName()).write(1, reason).get());
		} else {
			sender.sendMessage(I18n.UOFF.get());
		}
	}

}
