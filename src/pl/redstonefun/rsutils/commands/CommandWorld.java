package pl.redstonefun.rsutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="world",description="Teleportuje na spawn podanego świata")
public class CommandWorld implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{1,2};
	}

	@Override
	public Sender getSenders() {
		return Sender.PLAYER;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		
		User user = RSUtils.getUser((Player)sender);
		if(user.hasPermission("rsutils.world")){
			World world = Bukkit.getWorld(args.get(0));
			if(world == null){
				user.sendMessage("&4Podany świat nie istnieje!");
				return;
			}
			
			user.teleport(world);
			
		}
	}

}
