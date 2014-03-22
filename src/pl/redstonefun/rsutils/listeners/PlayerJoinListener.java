package pl.redstonefun.rsutils.listeners;

import java.text.ParseException;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import pl.redstonefun.rsutils.api.RSListener;
import pl.redstonefun.rsutils.calendar.CalendarEx;
import pl.redstonefun.rsutils.message.I18n;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.yaml.YAML;

@RSListener
public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e){
		if(e.getResult() == PlayerLoginEvent.Result.KICK_BANNED){
			YAML.type bans = YAML.type.BANS;
			String name = e.getPlayer().getName().toLowerCase();
			String forr = YAML.getString(bans, name + ".for");
			String reason = YAML.getString(bans, name + ".reason");
			if(forr.equals("forever")){
				e.setKickMessage(I18n.UBANNED.getE().write(0, reason).get());
			} else {
				CalendarEx ex = new CalendarEx();
				try {
					ex.setFromString(forr);
					if(ex.isLaterFromNow()){
						YAML.set(bans, name, null);						
						try {
							YAML.saveAndReload(bans);
						} catch (Exception e1) {}						
						e.getPlayer().setBanned(false);
						e.allow();
					} else {
						e.setKickMessage(I18n.UTBANNED.getE().write(0, ex.getInString()).write(1, reason).get());
					}
				} catch (ParseException e2) {
					e.setKickMessage(I18n.KICKDERROR.get());
					e2.printStackTrace();
				}				
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		User user = new User(e.getPlayer());
		if(e.getPlayer().hasPlayedBefore()){
			e.setJoinMessage(I18n.JOIN.getE().write(0, user.getColoredName()).get());
		} else {
			e.setJoinMessage(I18n.FJOIN.getE().write(0, user.getColoredName()).get());
		}
		user.sendMessage("&cWitaj na serwerze RedstoneFun.pl!");
		user.sendMessage("&a" + Bukkit.getOnlinePlayers().length + " na " + Bukkit.getMaxPlayers() + " jest dostępnych. Unikalnych wejść: " + Bukkit.getOfflinePlayers().length);
		user.login();
	}
	
	//TODO Tymczasowo
	@EventHandler
	public void onDisconnect(PlayerQuitEvent e){
		new User(e.getPlayer()).left();
	}
	
	
}
