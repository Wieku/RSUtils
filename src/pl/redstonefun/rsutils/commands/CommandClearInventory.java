package pl.redstonefun.rsutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;

@RSCommand(command = "clearinventory", description="Czyœci ekwipunek", aliases = {"ci"})
public class CommandClearInventory implements Command{

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		if(sender instanceof Player){
			ci((Player)sender);
		}
	}
	
	private void ci(Player player){
		player.getInventory().clear();
	}
	
}
