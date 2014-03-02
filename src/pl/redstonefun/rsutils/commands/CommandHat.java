package pl.redstonefun.rsutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command = "hat", description = "Zmienia twoj¹ czapkê")
public class CommandHat implements Command {

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		
		if(sender instanceof Player){
			User user = new User((Player) sender);
			if(user.hasPermission("tsutils.hat")){
				ItemStack prevHat = user.getHat();
				ItemStack currHat = user.getItemInHand();
				user.setHat(currHat);
				user.setItemInHand(prevHat);
				user.sendMessage("&aCiesz siê now¹ czapk¹!");
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
		return 0;
	}
	
	@Override
	public Object[] getSenders() {
		return new Object[]{Player.class};
	}
}
