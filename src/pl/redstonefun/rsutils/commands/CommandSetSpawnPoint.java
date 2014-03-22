package pl.redstonefun.rsutils.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="setspawnpoint", description="Ustawia spawn dla świata", aliases={"spp"})
public class CommandSetSpawnPoint implements Command {

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
		if(user.hasPermission("rsutils.setspawnpoint")){
			Location l = user.getLocation();
			l.getWorld().setSpawnLocation(l.getBlockX(), l.getBlockY(), l.getBlockZ());
			user.sendMessage("&aPrzestawiono spawnpointa!");
		}
	}
}