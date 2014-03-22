package pl.redstonefun.rsutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.message.I18n;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command = "hat", description = "Zmienia twoją czapkę")
public class CommandHat implements Command {

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {	
		User user = RSUtils.getUser((Player) sender);
		if(user.hasPermission("tsutils.hat")){
			ItemStack prevHat = user.getHat();
			ItemStack currHat = user.getItemInHand();
			user.setHat(currHat);
			user.setItemInHand(prevHat);
			user.sendMessage(I18n.NEWHAT.get());
		}
	}

	@Override
	public int[] getMinMax() {
		return new int[]{0,0};
	}

	@Override
	public Sender getSenders() {
		return Sender.PLAYER;
	}
}
