package pl.redstonefun.rsutils.commands;

import java.text.ParseException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.calendar.CalendarEx;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.yaml.YAML;

@RSCommand(command = "tempban", description="Banuje gracza tymczasowo", aliases={"tban"})
public class CommandTempban implements Command{

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
			if(!RSUtils.getUser((Player)sender).hasPermission("rsutils.tempban")){
				return;
			}
		}
				
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args.get(0));
				
		String reason = "Admin ma zawsze rację!";
				
		if(args.length > 2){
			reason = args.getFT(2, args.length-1, " ");
		}
			
		String name = offlinePlayer.getName().toLowerCase();
			
		String time = args.get(1);
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
