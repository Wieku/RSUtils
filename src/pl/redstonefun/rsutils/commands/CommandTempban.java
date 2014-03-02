package pl.redstonefun.rsutils.commands;

import java.text.ParseException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.calendar.CalendarEx;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.yaml.YAML;

@RSCommand(command = "tempban", description="Banuje gracza tymczasowo", aliases={"tban"})
public class CommandTempban implements Command{

	@Override
	public int getMin() {
		return 1;
	}

	@Override
	public int getMax() {
		return -1;
	}
	
	@Override
	public Object[] getSenders() {
		return new Object[]{Player.class, ConsoleCommandSender.class};
	}
	
	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		
		if(!(sender instanceof BlockCommandSender)){
			
			if(sender instanceof Player){
				User user = new User((Player)sender);
				if(!user.hasPermission("rsutils.tempban")){
					user.sendMessage(Messages.hasNoPermission);
					return;
				}
			}
				
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
				
			String reason = "Admin ma zawsze racjê!";
				
			if(args.length > 2){
				reason = "";
				for(int i = 2; i < args.length; i++){
					reason += (i==2?"":" ") + args[i];
				}
			}
			
			String name = offlinePlayer.getName().toLowerCase();
				
			String time = args[1];
			CalendarEx cx = new CalendarEx();
			cx.setToNow();
			
			try {
					
				if(time.contains("/")){
					for(String i : time.split("/")){
						cx.add(i);
					}
				} else {
					cx.add(time);
				}
					
				YAML.set(YAML.type.BANS, name + ".for", cx.getInString());
				YAML.set(YAML.type.BANS, name + ".reason", reason);
				YAML.set(YAML.type.BANS, name + ".who", sender.getName());
					
				try {
					YAML.saveAndReload(YAML.type.BANS);
				} catch (Exception e) {
					sender.sendMessage(Messages.saveFileError);
					e.printStackTrace();
				}
				
				offlinePlayer.setBanned(true);
				
				if(offlinePlayer.isOnline()){
					offlinePlayer.getPlayer().kickPlayer(Messages.youAreTempBanned.replace("%time", cx.getInString()).replace("%reason", reason));
				}
				Bukkit.broadcastMessage(Messages.userTempBanned.replace("%user", name).replace("%reason", reason).replace("%time", cx.getInString()));
			} catch (ParseException e){
				sender.sendMessage(ChatColor.RED + "Wadliwy format czasu!");
			}				
		}		
	}
}
