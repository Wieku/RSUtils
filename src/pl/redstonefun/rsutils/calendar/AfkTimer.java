package pl.redstonefun.rsutils.calendar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.yaml.YAML;

public class AfkTimer implements Runnable{

	@Override
	public void run() {
		
		long time = (YAML.isSet(YAML.type.CONFIG, "afktime")?YAML.getInteger(YAML.type.CONFIG, "afktime"):10) * 1000;
		
		for(Player player : Bukkit.getOnlinePlayers()){
			User user = new User(player);
			if(!user.isAfk()){
				if(user.hasPermissionSilent("rsutils.afk")){
					if((System.currentTimeMillis() - user.getLastActivity()) >= time){
						user.setAfk(true);
					}
				}
			}
			
		}
		
	}

}
