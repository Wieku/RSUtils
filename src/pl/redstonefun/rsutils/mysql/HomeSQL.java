package pl.redstonefun.rsutils.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import pl.redstonefun.rsutils.warp.Warp;

public class HomeSQL {

	
	private static Connection getConnection(){
		return ConnectionGetter.getInstance().get();
	}
	
	private static void release(Connection con){
		ConnectionGetter.getInstance().release(con);
	}
	
	public static boolean set(String player, Warp warp){
		Connection conn = getConnection();
		try{
			PreparedStatement st = conn.prepareStatement("INSERT INTO `rs`.`rs_home` (`id`, `player`, `name`, `x`, `y`, `z`, `pitch`, `yaw`, `world`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?);");
			st.setString(1, player.toLowerCase());
			st.setString(2, warp.getName().toLowerCase());
			st.setDouble(3,	warp.getX());
			st.setDouble(4, warp.getY());
			st.setDouble(5, warp.getZ());
			st.setFloat(6, warp.getPitch());
			st.setFloat(7, warp.getYaw());
			st.setString(8, warp.getWorldName());
			st.executeUpdate();
			st.close();
			release(conn);
			return true;
		}catch (SQLException e){
			return false;
		}
		
	}
	
	public static HashMap<String, Warp> getHomes(String playerName){
		Connection con = getConnection();
		HashMap<String, Warp> map = new HashMap<String, Warp>();
		try {
			PreparedStatement st = con.prepareStatement("SELECT * FROM `rs_home` WHERE `player`=?");
			st.setString(1, playerName.toLowerCase());
			ResultSet set = st.executeQuery();
			
			while(set.next()){
				String name = set.getString("name");
				map.put(name, new Warp(name, new Location(
						Bukkit.getWorld(set.getString("world")), set.getDouble("x"),
							set.getDouble("y"), set.getDouble("z"), set.getFloat("yaw"), set.getFloat("pitch"))));
			}
			set.close();
			st.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
		release(con);
		return map;
	}
	
	public static boolean deleteHome(String playerName, String name){
		int id = getHomeID(playerName, name);
		
		if(id < 0){
			return false;
		}
		
		Connection con = getConnection();
		try {
			PreparedStatement st1 = con.prepareStatement("DELETE FROM `rs_home` WHERE `id`=? LIMIT 1");
			st1.setInt(1, id);
			st1.executeUpdate();
			st1.close();
			release(con);
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			release(con);
		}
		return false;
	}
	
	
	public static int getHomeID(String playerName, String name){
		Connection con = getConnection();
		try {
			PreparedStatement st = con.prepareStatement("SELECT * FROM `rs_home` WHERE `player`=? AND `name`=?");
			st.setString(1, playerName.toLowerCase());
			st.setString(2, name.toLowerCase());
			ResultSet set = st.executeQuery();
			
			int id = -1;
			
			if(set.last()){
				if(set.getRow() > 0){
					set.first();
					id = set.getInt("id");
				}
			}
			
			set.close();
			st.close();
			release(con);
			return id;
		} catch (SQLException e){
			e.printStackTrace();
		}
		return -1;
	}
	
	
	public static boolean replace(String playerName, Warp warp){
		deleteHome(playerName, warp.getName().toLowerCase());
		return set(playerName, warp);
	}
	
}
