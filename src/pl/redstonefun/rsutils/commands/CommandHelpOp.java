package pl.redstonefun.rsutils.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="helpop", description="Wysyła wadomość do adminów")
public class CommandHelpOp implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{1,-1};
	}

	@Override
	public Sender getSenders() {
		return new Sender(Sender.PLAYER);
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		
		User user = RSUtils.getUser((Player)sender);
		
		if(!user.hasPermission("rsutils.helpop")){
			return;
		}
	
		String message = args.getFT(0, args.length-1, " ");
		
		for(User op : RSUtils.getUsers()){
			if(op.hasPermissionSilent("rsutils.helpop.op")){
				op.sendMessage(ChatColor.GOLD + "[HELPOP] " + user.getColoredName() +ChatColor.RESET + ChatColor.WHITE + ":" +ChatColor.GOLD + message);
			}
		}
		
	}

}
