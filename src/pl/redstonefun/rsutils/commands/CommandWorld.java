package pl.redstonefun.rsutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="world",description="Teleportuje na spawn podanego œwiata")
public class CommandWorld implements Command {

	@Override
	public int getMin() {
		return 1;
	}

	@Override
	public int getMax() {
		return 2;
	}

	@Override
	public Object[] getSenders() {
		return new Object[]{Player.class};
	}

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		
		User user = new User((Player)sender);
		if(user.hasPermission("rsutils.world")){
			World world = Bukkit.getWorld(args[0]);
			if(world == null){
				user.sendMessage("&4Podany œwiat nie istnieje!");
				return;
			}
			
			user.teleport(world);
			
		}
	}

}
