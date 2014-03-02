package pl.redstonefun.rsutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="more", description="Zwiêksza iloœæ przedmiotu w d³oni do stacka")
public class CommandMore implements Command {

	@Override
	public int getMin() {
		return 0;
	}

	@Override
	public int getMax() {
		return 0;
	}

	@Override
	public Object[] getSenders() {
		return new Object[]{Player.class};
	}

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		User user = new User((Player)sender);
		if(user.hasPermission("rsutils.more")){
			ItemStack item = user.getItemInHand();
			item.setAmount(item.getMaxStackSize());
			user.setItemInHand(item);
			user.sendMessage("&aCiesz siê stackiem!");
		}
	}

}
