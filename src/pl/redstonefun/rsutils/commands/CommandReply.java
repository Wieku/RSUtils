package pl.redstonefun.rsutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command = "reply", description="Odpowiedź do ostatniego nadawcy", aliases={"r"})
public class CommandReply implements Command{

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		
			User user = RSUtils.getUser((Player) sender);
			
			if(user.hasPermission("rsutils.reply")){
				user.reply(args.getFT(0, args.length-1, " "));
			}
		
	}

	@Override
	public int[] getMinMax() {
		return new int[]{0,-1};
	}

	@Override
	public Sender getSenders() {
		return Sender.PLAYER;
	}
}
