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

@RSCommand(command="kill", description="Zabija gracza")
public class CommandKill implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{0,1};
	}
	
	@Override
	public Sender getSenders() {
		return Sender.ALL;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		if(args.length == 0){
			if(sender instanceof Player){
				RSUtils.getUser((Player)sender).kill();
			} else {
				sender.sendMessage(I18n.UCANT.get());
			}
		} else {
			if(sender instanceof Player){
				if(!RSUtils.getUser((Player)sender).hasPermission("rsutils.kill")){
					return;
				}
			}
			User target = RSUtils.getUser(args.get(0));
			if(target != null){
				target.kill();
			} else {
				sender.sendMessage(I18n.UOFF.get());
			}
		}	
	}

}