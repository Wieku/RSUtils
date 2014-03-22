package pl.redstonefun.rsutils.warp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import pl.redstonefun.rsutils.yaml.YAML;

public class Warp {
	
	private Location location;
	private String name;
	
	public Warp(String name){
		this.name = name;
		YAML.type type = YAML.type.WARPS;		
		World world = Bukkit.getWorld(YAML.getString(type, name+".world"));
		double x = YAML.getDouble(type, name+".x");
		double y = YAML.getDouble(type, name+".y");
		double z = YAML.getDouble(type, name+".z");
		float pitch = YAML.getFloat(type, name+".pitch");
		float yaw = YAML.getFloat(type, name+".yaw");
		location = new Location(world, x, y, z, yaw, pitch);
	}
	
	public Warp(String name, Location loc){
		this.name = name;
		location = loc;
	}
	
	public Location toLocation(){
		return location;
	}
	
	public String getName(){
		return name;
	}
}
