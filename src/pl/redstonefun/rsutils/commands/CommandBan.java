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

@RSCommand(command = "ban", description="Banuje gracza")
public class CommandBan implements Command{

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		
		if(!(sender instanceof BlockCommandSender)){
			
			if(sender instanceof Player){
				User user = new User((Player)sender);
				if(!user.hasPermission("rsutils.ban")){
					user.sendMessage(Messages.hasNoPermission);
					return;
				}
			}
			
			if(args.length < 1){
				sender.sendMessage(Messages.notEnoughArguments);
			} else {
				
				OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
				
				String reason = "Admin ma zawsze racjê!";
				
				if(args.length > 1){
					reason = "";
					for(int i = 1; i < args.length; i++){
						reason += (i==1?"":" ") + args[i];
					}
				}
				
				String name = offlinePlayer.getName().toLowerCase();
				
				YAML.set(YAML.type.BANS, name + ".for", "forever");
				YAML.set(YAML.type.BANS, name + ".reason", reason);
				YAML.set(YAML.type.BANS, name + ".who", sender.getName());
				
				try {
					YAML.saveAndReload(YAML.type.BANS);
				} catch (Exception e) {
					sender.sendMessage(Messages.saveFileError);
					e.printStackTrace();
				}
				
				offlinePlayer.setBanned(true);
				if(offlinePlayer.isOnline()) offlinePlayer.getPlayer().kickPlayer(Messages.youAreBanned.replace("%reason", reason));
				Bukkit.broadcastMessage(Messages.userBanned.replace("%user", name).replace("%reason", reason));
			}
			
		}
		
	}

}
