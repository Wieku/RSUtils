package pl.redstonefun.rsutils.user;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import pl.redstonefun.rsutils.main.RSUtils;
public class TabList implements Runnable {

	SimpleDateFormat form = new SimpleDateFormat("HH:mm:ss");
	
	public TabList() {
		form.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
	}
	
	
	@Override
	public void run() {

		List<User> listPl = Arrays.asList(RSUtils.sortByRank(RSUtils.getVisibleUsers()));
		
		for(User user : RSUtils.getUsers()){


			long liv = user.getPlayer().getTicksLived()/20;
			long sec = liv % 60;
			long min = (liv / 60 ) % 60;
			long hour = (liv / 3600 ) % 24;
			String session = String.format("%2d:%2d:%2d", hour, min, sec).replace(" ","0");

			user.sendHeaderAndFooter(
					ChatColor.GOLD+"      Witaj na serwerze RedstoneFUN!!!      \n"+
							ChatColor.GREEN + "Obecnie graczy: "+listPl.size()+"/"+Bukkit.getMaxPlayers()+"\n"+
							ChatColor.AQUA + "Dostępni gracze:",
					"IP >> "+ChatColor.RESET+ChatColor.GRAY+"redstonefun.pl\n"+ChatColor.RESET+
							"Czas sesji: "+ChatColor.RESET+ChatColor.YELLOW+session + "\n"+ChatColor.RESET +
							"Godzina: "+ChatColor.RESET+ChatColor.YELLOW+form.format(new Date()) + "\n"+ChatColor.RESET +
							"Twoja ranga: "+ChatColor.RESET+user.getGroupColored() +
							(user.isVanished()?("\n"+ChatColor.RED + "Jesteś obecnie ukryty"):"")
			);

		}
		
	}
	
}
