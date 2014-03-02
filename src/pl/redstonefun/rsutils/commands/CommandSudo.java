package pl.redstonefun.rsutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="sudo", description="Wskazany gracz wykona komendê")
public class CommandSudo implements Command {

	@Override
	public int getMin() {
		return 2;
	}

	@Override
	public int getMax() {
		return -1;
	}

	@Override
	public Object[] getSenders() {
		return null;
	}

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		
		if(sender instanceof Player){
			if(!new User((Player)sender).hasPermission("rsutils.sudo")){
				return;
			}
		}
		
		Player player = Bukkit.getPlayer(args[0]);
		if(player != null){
			User user = new User(player);
			String comm = "";
			for(int i = 1; i< args.length;i++){
				comm += (i==1?"":" ") + args[i];
			}
			user.executeCommand(comm);
		} else {
			sender.sendMessage(Messages.userOffline);
		}
		
	}

}
