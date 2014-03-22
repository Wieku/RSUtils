package pl.redstonefun.rsutils.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import pl.redstonefun.rsutils.api.RSListener;
import pl.redstonefun.rsutils.user.User;

@RSListener
public class PlayerAfkListener implements Listener{

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerMove(PlayerMoveEvent e){
		User user = new User(e.getPlayer());
		
		if(user.isAfk()){
			if(e.getFrom().distanceSquared(e.getTo()) > 0.01){
				user.setAfk(false);
			}
					
		} else {
			user.updateLastActivity();
		}
		
	}
	
}
