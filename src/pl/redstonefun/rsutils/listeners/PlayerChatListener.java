package pl.redstonefun.rsutils.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import pl.redstonefun.rsutils.api.RSListener;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.yaml.YAML;

@RSListener
public class PlayerChatListener implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e){
		
		User user = new User(e.getPlayer());
		
		if(user.isMuted()){
			YAML.type type = YAML.type.BANS;
			String mute = user.getName().toLowerCase() + ".mute";
			user.sendMessage("&7Jesteś wyciszony do " + YAML.getString(type, mute + ".for") + " za: " + YAML.getString(type, mute + ".reason"));
			e.setCancelled(true);
			return;
		}
		
		String format = user.getChatFormat();
		
		String message = e.getMessage();
		
		format = format.replace("{MESSAGE}", message);
		
		e.setFormat((user.hasPermissionSilent("rsutils.chatcolor")?ChatColor.translateAlternateColorCodes('&', format):format));
		
		user.updateListName();
		
	}
}
