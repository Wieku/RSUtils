package pl.redstonefun.rsutils.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import pl.redstonefun.rsutils.api.RSListener;
import pl.redstonefun.rsutils.user.User;

@RSListener
public class SignColoringListener implements Listener {
	
	@EventHandler
	public void onSignEdit(SignChangeEvent e){
		if(new User(e.getPlayer()).hasPermissionSilent("rsutils.signs.color")){
			for(int i=0;i<e.getLines().length;i++){
				e.setLine(i, ChatColor.translateAlternateColorCodes('&', e.getLine(i)));
			}
		}
	}
}
