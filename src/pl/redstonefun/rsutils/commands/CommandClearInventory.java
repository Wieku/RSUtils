package pl.redstonefun.rsutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;

@RSCommand(command = "clearinventory", description="Czy�ci ekwipunek", aliases = {"ci"})
public class CommandClearInventory implements Command{

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		if(sender instanceof Player){
			((Player)sender).getInventory().clear();
		}
	}
	
}
