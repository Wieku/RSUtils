package pl.redstonefun.rsutils.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.yaml.YAML;

@RSCommand(command = "setwarp", description="Ustawia warpa serwerowego", aliases={"swarp"})
public class CommandSetWarp implements Command{

	@Override
	public int getMin() {
		return 1;
	}

	@Override
	public int getMax() {
		return 1;
	}

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		
		User user = new User((Player) sender);
		if(user.hasPermission("rsutils.warp.set")){
			Location loc = user.getLocation();
			YAML.type type = YAML.type.WARPS;
			YAML.set(type, args[0]+".world", loc.getWorld().getName());
			YAML.set(type, args[0]+".x", loc.getX());
			YAML.set(type, args[0]+".y", loc.getY());
			YAML.set(type, args[0]+".z", loc.getZ());
			YAML.set(type, args[0]+".pitch", loc.getPitch());
			YAML.set(type, args[0]+".yaw", loc.getYaw());
			try {
				YAML.saveAndReload(type);
				user.sendMessage("&aUtworzono!");
			} catch (Exception e) {
				user.sendMessage("&4Nie mo¿na ustawiæ warpa!");
				e.printStackTrace();
				return;
			}
		}
	}

}
