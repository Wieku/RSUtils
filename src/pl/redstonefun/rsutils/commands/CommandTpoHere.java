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

@RSCommand(command="tpohere", description="Teleportuje gracza do ciebie, omijając jego niewidzalność")
public class CommandTpoHere implements Command{

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
		if(user.hasPermission("rsutils.tp.tpohere")){
			
			User us= RSUtils.getUser(args.get(0));
			
			if(us != null){
				us.teleport(user);
			} else {
				user.sendMessage(I18n.UOFF.get());
			}
			
		}
	}

}
