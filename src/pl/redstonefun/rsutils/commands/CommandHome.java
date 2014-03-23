package pl.redstonefun.rsutils.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.mysql.ConnectionGetter;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.warp.Warp;

@RSCommand(command="home")
public class CommandHome implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{0,1};
	}

	@Override
	public Sender getSenders() {
		return Sender.PLAYER;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		User user = RSUtils.getUser((Player)sender);
		if(user.hasPermission("rsutils.home.tp")){
			Connection connection = ConnectionGetter.getInstance().get();
			if(args.length == 0){
				try {
					PreparedStatement st = connection.prepareStatement("SELECT * FROM `rs_home` WHERE `player`=?");
					st.setString(1, user.getName().toLowerCase());
					ResultSet set = st.executeQuery();
					String text1 = ChatColor.GREEN + "Lista home'ów: ";
					String list = "";
					while(set.next()){
						list = list + ChatColor.GOLD + ChatColor.BOLD + set.getString("name") + ChatColor.RESET + ChatColor.GRAY + ", ";
					}
					set.close();
					st.close();
					ConnectionGetter.getInstance().release(connection);
					user.sendMessage(text1);
					user.sendMessage(list);
				} catch(SQLException e){
					e.printStackTrace();
				}
			} else {				
				try {
					PreparedStatement st;
					st = connection.prepareStatement("SELECT * FROM `rs_home` WHERE `player`=? AND `name`=?");
					st.setString(1, user.getName().toLowerCase());
					st.setString(2, args.get(0).toLowerCase());
					ResultSet set = st.executeQuery();
					
					int count = 0;
					
					if(set.last()){
						count = set.getRow();
						set.first();
					}
					
					System.out.println(count);
					if(count == 0){
						user.sendMessage("&4Home nie istnieje");
						return;
					}
					
					Warp warp = new Warp(args.get(0).toLowerCase(), new Location(Bukkit.getWorld(set.getString("world")), set.getDouble("x"), set.getDouble("y"), set.getDouble("z"), set.getFloat("yaw"), set.getFloat("pitch")));
					set.close();
					st.close();
					ConnectionGetter.getInstance().release(connection);
					user.teleport(warp);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
			}
		}
	}

}
