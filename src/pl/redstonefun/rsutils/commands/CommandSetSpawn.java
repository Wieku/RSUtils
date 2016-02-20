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
import pl.redstonefun.rsutils.yaml.YAML;

@RSCommand(command="setspawn", description="Ustawia spawn dla serwera", aliases={"sspawn"})
public class CommandSetSpawn implements Command {

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
		if(user.hasPermission("rsutils.setspawn")){
			Location l = user.getLocation();
			l.getWorld().setSpawnLocation(l.getBlockX(), l.getBlockY(), l.getBlockZ());

			YAML.set(YAML.type.CONFIG, "spawn", l.getWorld().getName());
			try {
				YAML.saveAndReload(YAML.type.CONFIG);
			} catch (Exception e) {
				e.printStackTrace();
			}
			user.sendMessage("&aPrzestawiono spawna!");
		}
	}
}