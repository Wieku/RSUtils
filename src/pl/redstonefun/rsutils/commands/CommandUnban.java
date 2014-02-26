package pl.redstonefun.rsutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.yaml.YAML;

@RSCommand(command = "unban", description="Odbanowuje gracza")
public class CommandUnban implements Command{

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		
		if(!(sender instanceof BlockCommandSender)){
			
			if(sender instanceof Player){
				User user = new User((Player)sender);
				if(!user.hasPermission("rsutils.unban")){
					user.sendMessage(Messages.hasNoPermission);
					return;
				}
			}
			
			if(args.length < 1){
				sender.sendMessage(Messages.notEnoughArguments);
			} else {
				
				OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
				
				String name = offlinePlayer.getName().toLowerCase();
				
				YAML.set(YAML.type.BANS, name, null);
				
				try {
					YAML.saveAndReload(YAML.type.BANS);
				} catch (Exception e) {
					sender.sendMessage(Messages.saveFileError);
					e.printStackTrace();
				}
				
				offlinePlayer.setBanned(false);
				Bukkit.broadcastMessage(Messages.unBanned.replace("%user", name));
			}
			
		}
		
	}

	@Override
	public int getMin() {
		return 1;
	}

	@Override
	public int getMax() {
		return 1;
	}

}
