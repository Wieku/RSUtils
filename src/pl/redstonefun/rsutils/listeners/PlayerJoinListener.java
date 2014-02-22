package pl.redstonefun.rsutils.listeners;

import java.text.ParseException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import pl.redstonefun.rsutils.calendar.CalendarEx;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.yaml.YAML;
import pl.redstonefun.rsutils.yaml.YAML.type;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e){
		if(e.getResult() == PlayerLoginEvent.Result.KICK_BANNED){
			String name = e.getPlayer().getName().toLowerCase();
			String forr = YAML.getString(type.BANS, name + ".for");
			String reason = YAML.getString(type.BANS, name + ".reason");
			if(forr.equals("forever")){
				e.setKickMessage(Messages.youAreBanned.replace("%reason", reason));
			} else {
				CalendarEx ex = new CalendarEx();
				try {
					ex.setFromString(forr);
					if(ex.isLaterFromNow()){
						
						YAML.set(YAML.type.BANS, name, null);
						
						try {
							YAML.saveAndReload(YAML.type.BANS);
						} catch (Exception e1) {}
						
						e.getPlayer().setBanned(false);
						e.allow();
					} else {
						e.setKickMessage(Messages.youAreTempBanned.replace("%reason", reason).replace("%time", ex.getInString()));
					}
				} catch (ParseException e2) {
					e.setKickMessage("Problem z rozpoznaniem daty! Zg³oœ to administracji, np. na stronie redstonefun.pl!");
					e2.printStackTrace();
				}
				
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		e.setJoinMessage(Messages.join.replace("%user", e.getPlayer().getName()));
	}
}
