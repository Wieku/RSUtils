package pl.redstonefun.rsutils.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerLessListener implements Listener {
	
	@EventHandler
	public void onPlayerFalling(PlayerMoveEvent e){
		Location loc = e.getPlayer().getLocation();
		if(loc.getY() < -10.0D){
			loc.add(0, 30, 0);
			e.getPlayer().teleport(loc);
		}
	}
}
