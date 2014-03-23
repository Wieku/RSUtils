package pl.redstonefun.rsutils.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

@RSCommand(command="sethome",description="Ustawia ...")
public class CommandSetHome implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{1,1};
	}

	@Override
	public Sender getSenders() {
		return Sender.PLAYER;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
	
		User user = RSUtils.getUser((Player)sender);
		
		String playerName = user.getName().toLowerCase();
		
		if(user.hasPermission("rsutils.home.set")){
			
			
			if(user.hasPermissionSilent("rsutils.home.set.amount.unlimited")){
				
				createHome(user, args.get(0));
				
			} else {
				for(int h=1;h<=1000;h++){
					if(user.hasPermissionSilent("rsutils.home.set.amount."+h)){
						
						if(getValuesAmount(playerName) <= h || homeExists(playerName, args.get(0))){
							
							createHome(user, args.get(0));
							
						} else {
							user.sendMessage("&4 Masz już zbyt wiele home'ów");
						}
					}
				}
			}
		}
		
	}

	private void createHome(User user, String name) {
		Location loc = user.getLocation();
		
		String plName = user.getName().toLowerCase();
		String world = loc.getWorld().getName();
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		float pitch = loc.getPitch();
		float yaw = loc.getYaw();
		
		Connection connection = ConnectionGetter.getInstance().get();
		try {
			PreparedStatement st = connection.prepareStatement("INSERT INTO `rs`.`rs_home` (`id`, `player`, `name`, `x`, `y`, `z`, `pitch`, `yaw`, `world`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?);");
			st.setString(1, plName);
			st.setString(2, name);
			st.setDouble(3, x);
			st.setDouble(4, y);
			st.setDouble(5, z);
			st.setFloat(6, pitch);
			st.setFloat(7, yaw);
			st.setString(8, world);
			st.executeUpdate();
			st.close();
			ConnectionGetter.getInstance().release(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int getValuesAmount(String playerName) {
		Connection connection = ConnectionGetter.getInstance().get();
		try {
			PreparedStatement st = connection.prepareStatement("SELECT * FROM `rs_home` WHERE `player`=?");
			st.setString(1, playerName);
			ResultSet set = st.executeQuery();
			int ret = set.getFetchSize();
			set.close();
			st.close();
			ConnectionGetter.getInstance().release(connection);
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private boolean homeExists(String playerName, String homeName) {
		Connection connection = ConnectionGetter.getInstance().get();
		try {
			PreparedStatement st = connection.prepareStatement("SELECT * FROM `rs_home` WHERE `player`=? AND `name'=?");
			st.setString(1, playerName);
			st.setString(2, homeName);
			ResultSet set = st.executeQuery();
			boolean ret = (set.getFetchSize()>0);
			set.close();
			st.close();
			ConnectionGetter.getInstance().release(connection);
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
