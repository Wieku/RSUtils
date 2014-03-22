package pl.redstonefun.rsutils.profiler;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;

import pl.redstonefun.rsutils.main.RSUtils;

public class Ticks implements Runnable{

	long lastExec = 0;
	static long lastTickTime = 50;
	static long avgTickTime = 50000000;
	long avgResTickTime = 50000000;
	static double currentTicks =20.0;
	static double avgTicks = 20.0;
	
	public Ticks(){
		Bukkit.getScheduler().runTaskTimerAsynchronously(RSUtils.instance, new SecTimer(), 0, 20);
	}
	
	@Override
	public void run() {
		long timeNow = System.nanoTime();
		if(lastExec == 0){
			lastExec = timeNow-50;
		}		
		lastTickTime = timeNow - lastExec;
		avgResTickTime = (avgResTickTime + lastTickTime) / 2;
		avgTickTime = (avgTickTime + avgResTickTime) / 2;
		lastExec = timeNow;
		
	}
	
	public static double getAverageTicks(boolean round){
		if(round){
			return Double.parseDouble(new DecimalFormat("##.##").format(avgTicks).replace(",", "."));
		} else {
			return avgTicks;
		}
	}
	
	public static double getCurrentTicks(boolean round){
		if(round){
			return Double.parseDouble(new DecimalFormat("##.##").format(currentTicks).replace(",", "."));
		} else {
			return currentTicks;
		}
	}
	
	public static long getAverageTickTime(){
			return avgTickTime;
	}
	
	public static long getLastTickTime(){
		return lastTickTime;
	}
	
	public class SecTimer implements Runnable{
		@Override
		public void run() {
			currentTicks = (((double)1000000000) / avgResTickTime);
			avgTicks = (avgTicks + currentTicks) / 2;
			avgResTickTime = 50000000;
		}		
	}
	
}
