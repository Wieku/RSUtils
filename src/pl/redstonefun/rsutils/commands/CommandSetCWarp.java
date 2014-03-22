package pl.redstonefun.rsutils.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.yaml.YAML;

@RSCommand(command="setcwarp",description="Ustawia ClockWarpa dla Warpa")
public class CommandSetCWarp implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{1,-1};
	}

	@Override
	public Sender getSenders() {
		return new Sender(Sender.PLAYER,Sender.CONSOLE);
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		
		if(sender instanceof Player){
			if(!RSUtils.getUser((Player) sender).hasPermission("rsutils.warp.set")){
				return;
			}
		}
	
		YAML.type tp = YAML.type.WARPS;
		String name = args.get(0).toLowerCase();
		
		
		if(YAML.isSet(tp, name)){
			
			String description = args.getFT(1, args.length-1, " ");
			
			YAML.set(tp, name + ".cwarp", "true");
			YAML.set(tp, name + ".cdescription", description);
			
			
			try {
				YAML.saveAndReload(tp);
			} catch (Exception e) {
				e.printStackTrace();
				sender.sendMessage(ChatColor.RED + "Problem z zapisem pliku Warpów!");
			}
			
			sender.sendMessage(ChatColor.GREEN + "Ustawiono ClockWarpa!");
			
		} else {
			sender.sendMessage(ChatColor.RED + "Podany Warp nie istnieje!");
		}
		
	}

}
