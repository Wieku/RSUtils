package pl.redstonefun.rsutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="more", description="Zwiększa ilość przedmiotu w dłoni do stacka")
public class CommandMore implements Command {

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
		User user = RSUtils.getUser((Player)sender);
		if(user.hasPermission("rsutils.more")){
			ItemStack item = user.getItemInHand();
			item.setAmount(item.getMaxStackSize());
			user.setItemInHand(item);
			user.sendMessage("&aCiesz się stackiem!");
		}
	}

}
