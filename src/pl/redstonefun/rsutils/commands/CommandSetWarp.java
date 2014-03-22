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

@RSCommand(command = "setwarp", description="Ustawia warpa serwerowego", aliases={"swarp"})
public class CommandSetWarp implements Command{

	@Override
	public int[] getMinMax() {
		return new int[]{1,1};
	}

	@Override
	public Sender getSenders() {
		return Sender.PLAYER;
	}
	
	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		
		User user = RSUtils.getUser((Player) sender);
		if(user.hasPermission("rsutils.warp.set")){
			Location loc = user.getLocation();
			YAML.type type = YAML.type.WARPS;
			String name = args.get(0).toLowerCase();
			YAML.set(type, name +".world", loc.getWorld().getName());
			YAML.set(type, name +".x", loc.getX());
			YAML.set(type, name +".y", loc.getY());
			YAML.set(type, name +".z", loc.getZ());
			YAML.set(type, name +".pitch", loc.getPitch());
			YAML.set(type, name +".yaw", loc.getYaw());
			try {
				YAML.saveAndReload(type);
				user.sendMessage("&aUtworzono!");
			} catch (Exception e) {
				user.sendMessage("&4Nie można ustawić warpa!");
				e.printStackTrace();
				return;
			}
		}
	}

}
