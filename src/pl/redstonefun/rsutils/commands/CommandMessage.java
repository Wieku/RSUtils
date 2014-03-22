package pl.redstonefun.rsutils.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command = "message", description="Wysyła wiadomość do gracza", aliases={"msg"})
public class CommandMessage implements Command{

	@Override
	public int[] getMinMax() {
		return new int[]{1,-1};
	}

	@Override
	public Sender getSenders() {
		return Sender.ALL;
	}
	
	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		User forWho = RSUtils.getUser(args.get(0));
			
		if(forWho == null){
			sender.sendMessage(Messages.userOffline);
			return;
		}
			
		String message = args.getFT(1, args.length-1, " ");
			
		if(sender instanceof Player){
			User user = RSUtils.getUser((Player)sender);
			if(!user.hasPermission("rsutils.msg")){
				return;
			}
			user.sendPrivateMessage(forWho, message);
		} else {
			
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.msgToHim.replace("%user", forWho.getColoredName())) + message);
			forWho.sendMessage(Messages.msgToMe.replace("%user", sender.getName()) + message);
				
		}
	}
	
}
