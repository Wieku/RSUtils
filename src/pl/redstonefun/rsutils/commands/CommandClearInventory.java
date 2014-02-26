package pl.redstonefun.rsutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command = "clearinventory", description="Czyœci ekwipunek", aliases = {"ci"})
public class CommandClearInventory implements Command{
	
	@Override
	public int getMin() {
		return 0;
	}

	@Override
	public int getMax() {
		return 0;
	}
	
	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		if(sender instanceof Player){
			User user  = new User((Player)sender);
			if(user.hasPermission("rsutils.clearinventory")){
				user.clearInventory();	
			}
		}
	}


	
}
