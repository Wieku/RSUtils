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
import pl.redstonefun.rsutils.warp.Warp;
import pl.redstonefun.rsutils.yaml.YAML;

@RSCommand(command="spawn", description="Teleportuje na spawn")
public class CommandSpawn implements Command {

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
		if(user.hasPermission("rsutils.spawn")){
			if(YAML.isSet(YAML.type.CONFIG, "spawn")){
				World world = Bukkit.getWorld(YAML.getString(YAML.type.CONFIG, "spawn"));
				if(world != null){
					user.teleport(new Warp("spawn", world.getSpawnLocation()));
				} else {
					user.sendMessage("&4Świat nie istnieje!");
				}
			} else {
				user.sendMessage("&4Spawn nie jest ustawiony!");
			}
		}
	}

}
