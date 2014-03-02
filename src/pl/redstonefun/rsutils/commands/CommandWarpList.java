package pl.redstonefun.rsutils.commands;

import java.util.TreeSet;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.yaml.YAML;

@RSCommand(command="warplist", description="Ustawia warpa serwerowego", aliases={"warps", "wlist"})
public class CommandWarpList implements Command {

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
		return null;
	}

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		
		if(sender instanceof Player){
			if(!new User((Player)sender).hasPermission("rsutils.warp.teleport")){
				return;
			}
		}
		String all = "";
		TreeSet<String> list = new TreeSet<String>(YAML.getMainKeys(YAML.type.WARPS));
		for (String name : list){
			all = all + ChatColor.GOLD + ChatColor.BOLD + name + ChatColor.RESET + ChatColor.GRAY + ", ";
		}		
		sender.sendMessage(ChatColor.AQUA + "Dostêpne warpy: " + all);
	}


}
