package pl.redstonefun.rsutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="list", description="Wyświetla listę graczy", aliases={"ls","players"})
public class CommandList implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{0,0};
	}

	@Override
	public Sender getSenders() {
		return Sender.ALL;
	}
	
	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		String lista="";
		for(User user : RSUtils.sortByRank(RSUtils.getVisibleUsers())){
			lista += (user.isAfk()?ChatColor.GRAY+"[AFK]":"") + user.getColoredName() + ChatColor.RESET + ChatColor.GRAY + ", ";
		}
		sender.sendMessage(ChatColor.GREEN+"Graczy na serwerze (" + RSUtils.getUsers().length +" na "+Bukkit.getMaxPlayers()+"):");
		sender.sendMessage(lista);

	}

}
