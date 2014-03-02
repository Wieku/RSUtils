package pl.redstonefun.rsutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.user.PlayerByRankSorter;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="list", description="Wyœwietla listê graczy", aliases={"ls","players"})
public class CommandList implements Command {

	@Override
	public int getMin() {
		return 0;
	}

	@Override
	public int getMax() {
		return 0;
	}

	@Override
	public Object[] getSenders() {
		return null;
	}
	
	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		Player[] players = Bukkit.getOnlinePlayers();
		String lista="";		
		for(Player play : new PlayerByRankSorter().sort(Bukkit.getOnlinePlayers())){
			User user = new User(play);
			String nick = /*(User.isAfk(play)?ChatColor.GRAY+"[AFK]":"") +*/ user.getColoredName();
			lista += nick + ChatColor.RESET + ChatColor.GRAY + ", ";
		}
		sender.sendMessage(ChatColor.GREEN+"Graczy na serwerze (" + players.length +" na "+Bukkit.getMaxPlayers()+"):");
		sender.sendMessage(lista);

	}

}
