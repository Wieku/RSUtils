package pl.redstonefun.rsutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command = "reply", description="Odpowiedü do ostatniego nadawcy", aliases={"r"})
public class CommandReply implements Command{

	@Override
	public void exec(CommandSender sender, String command, String[] args) {

		if(sender instanceof Player){
			
			User user = new User((Player) sender);
			
			if(user.hasPermission("rsutils.reply")){
			
				String message = "";
				
				for(int i=0;i<args.length;i++){
					message += (i==0?"":" ") + args[i];
				}
				
				user.reply(message);
			}
			
		} else {
			sender.sendMessage(Messages.hasNoPermission);
		}
		
	}

	@Override
	public int getMin() {
		return 0;
	}

	@Override
	public int getMax() {
		return -1;
	}
	
}
