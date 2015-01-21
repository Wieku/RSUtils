package pl.redstonefun.rsutils.calendar;

import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.yaml.YAML;

public class AfkTimer implements Runnable{

	@Override
	public void run() {
		
		long time = (YAML.isSet(YAML.type.CONFIG, "afktime")?YAML.getInteger(YAML.type.CONFIG, "afktime"):10) * 1000;
		
		for(User user : RSUtils.getUsers()){
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
