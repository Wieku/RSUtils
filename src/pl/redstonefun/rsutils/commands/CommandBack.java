package pl.redstonefun.rsutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="back", description="Wraca do ostatniej lokacji", aliases={"return"})
public class CommandBack implements Command {

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
		if(user.hasPermission("rsutils.back")){
			user.teleportToLast();
		}
	}

}
