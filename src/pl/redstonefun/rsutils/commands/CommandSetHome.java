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

@RSCommand(command="sethome",description="Ustawia ...")
public class CommandSetHome implements Command {

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
	
		User user = RSUtils.getUser((Player)sender);
		
		
		
		if(user.hasPermission("rsutils.home.set")){
			
			
			if(user.hasPermissionSilent("rsutils.home.set.amount.unlimited")){
				
				createHome(user, args.get(0));
				
			} else {
				for(int h=1;h<=1000;h++){
					if(user.hasPermissionSilent("rsutils.home.set.amount."+h)){
						
						if(getValuesAmount() <= h || homeExists(args.get(0))){
							
							createHome(user, args.get(0));
							
						} else {
							user.sendMessage("&4 Masz już zbyt wiele home'ów");
						}
					}
				}
			}
			
			
			
		}
		
	}

	private void createHome(User user, String name) {
		Location loc = user.getLocation();
		
		String plName = user.getName().toLowerCase();
		String world = loc.getWorld().getName();
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		float pitch = loc.getPitch();
		float yaw = loc.getYaw();
		
		YAML.type type = YAML.type.HOMES;
		
		YAML.set(type, plName + "." + name + ".world", world);
		YAML.set(type, plName + "." + name + ".x", x);
		YAML.set(type, plName + "." + name + ".y", y);
		YAML.set(type, plName + "." + name + ".z", z);
		YAML.set(type, plName + "." + name + ".yaw", yaw);
		YAML.set(type, plName + "." + name + ".pitch", pitch);
		//ZAPIS
		
	}

	private int getValuesAmount(String playerName) {
		
		return 0;
	}

	private boolean homeExists(String playerName, String homeName) {
		
		return false;
	}
	
	
	
	
}
