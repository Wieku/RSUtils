package pl.redstonefun.rsutils.user;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.main.RSUtils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

public class TabList implements Runnable {

	List<String> list = new ArrayList<String>();
	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat form = new SimpleDateFormat("HH:mm:ss");
	HashMap<Player, List<TabHandler>> data = new HashMap<Player, List<TabHandler>>();
	
	public TabList() {
		form.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
		list.add(ChatColor.BLUE + "============");
		list.add(ChatColor.BLUE + "============");
		list.add(ChatColor.BLUE + "============");
		
		list.add("Godzina: ");
		list.add("Serwer:");
		list.add("Sesja: ");
		
		list.add("[hour]");
		list.add(ChatColor.RED + "RedstoneFun");
		list.add("[session]");
		
		list.add(ChatColor.BLUE + "============");
		list.add(ChatColor.BLUE + "============");
		list.add(ChatColor.BLUE + "============");
	}
	
	
	@Override
	public void run() {
		
		for(Player player : Bukkit.getOnlinePlayers()){
			if(data.containsKey(player)){
				for(TabHandler han : data.get(player)){
					//System.out.println("REm " + han.a);
					sendPacket(player, han.a, han.b, false);
				}
			}
		}
		List<User> listPl = Arrays.asList(RSUtils.sortByRank(RSUtils.getVisibleUsers()));
		
		for(Player player : Bukkit.getOnlinePlayers()){
			List<TabHandler> handL = new ArrayList<TabHandler>();
			resetUniqueEnding();
			for(String f : list){
				TabHandler tb = new TabHandler();
				if(f.equals("[session]")){
					long liv = player.getTicksLived()/20;
					long sec = liv % 60;
					long min = (liv / 60 ) % 60;
					long hour = (liv / 3600 ) % 24;
					tb.a = ext(hour) + ":" + ext(min) + ":" + ext(sec) + getUniqueEnding();
				} else if(f.equals("[hour]")){
					tb.a = form.format(new Date()) + getUniqueEnding();
				} else {
					tb.a = f + getUniqueEnding();
				}
				tb.b = 0;
				handL.add(tb);
			}
			
			for(int i = 0;i < 60-list.size() && listPl.size() > i;i++){
				TabHandler tb = new TabHandler();
				tb.a = listPl.get(i).getListName();
				tb.b = 0;
				handL.add(tb);
			}
			
			int h = 60 - list.size() - listPl.size();
			if(h > 0){
				for(int g=0;g<h;g++){
					TabHandler tb = new TabHandler();
					tb.a = getUniqueEnding();
					tb.b = 0;
					handL.add(tb);
				}
			}
			
			for(TabHandler a : handL){
				//System.out.println("send " + a.a);
				sendPacket(player, a.a, a.b, true);
			}
			
			data.put(player, handL);
			
		}
		
	}
	
	public void sendPacket(Player player, String text, int ping, boolean bool){
		PacketContainer container = RSUtils.pManager.createPacket(PacketType.Play.Server.PLAYER_INFO);
		container.getStrings().write(0, text);
		container.getIntegers().write(0, ping);
		container.getBooleans().write(0, bool);
		
		try {
			RSUtils.pManager.sendServerPacket(player, container);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public String ext(long min){
		return String.format("%2s", Long.toString(min)).replace(" ", "0");
	}
	
	private static int uniqueEndingCounter = 0;
	private static int uniqueColorCounter = 0;

	public static void resetUniqueEnding() {
		uniqueColorCounter = 0;
		uniqueEndingCounter = 0;
	}

	public static String getUniqueEnding()
	{
		String uniqueEnding = "";
		for (int a = 0; a < uniqueEndingCounter; a++) 
		{
			uniqueEnding = " " + uniqueEnding;
		}
		uniqueEnding = ChatColor.values()[uniqueColorCounter] + uniqueEnding;
		uniqueColorCounter++;
		if(uniqueColorCounter >= 15)
		{
			uniqueColorCounter = 0;
			uniqueEndingCounter++;
		}
		return uniqueEnding;
	}
	
}
