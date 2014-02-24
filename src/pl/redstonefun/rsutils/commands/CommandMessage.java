package pl.redstonefun.rsutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command = "message", description="Wysy³a wiadomoœæ do gracza", aliases={"msg"})
public class CommandMessage implements Command{

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		
		if(args.length < 1){
			sender.sendMessage(Messages.notEnoughArguments);
		} else {
			
			User forWho = new User(Bukkit.getPlayer(args[0]));
			
			if(!forWho.isOnline()){
				sender.sendMessage(Messages.userOffline.replace("%user", args[0]));
				return;
			}
			
			String message = "";
			
			for(int i=1;i<args.length;i++){
				message += (i==1?"":" ") + args[i];
			}
			
			if(sender instanceof Player){
				User user = new User((Player)sender);
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
	
}
