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

@RSCommand(command = "clearinventory", description="Czy�ci ekwipunek", aliases = {"ci"})
public class CommandClearInventory implements Command{
	
	@Override
	public int[] getMinMax() {
		return new int[]{0,0};
	}
	
	@Override
	public Sender getSenders() {
		return Sender.PLAYER;
	}
	
	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		User user  = RSUtils.getUser((Player)sender);
		if(user.hasPermission("rsutils.clearinventory")){
			user.clearInventory();
			user.sendMessage(I18n.CLEAREDINV.get());
		}
	}

}
