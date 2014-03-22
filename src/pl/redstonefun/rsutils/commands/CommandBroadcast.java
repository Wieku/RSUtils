package pl.redstonefun.rsutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.message.I18n;

@RSCommand(command="broadcast", description="Wysyła wiadomość serwerową", aliases={"say"})
public class CommandBroadcast implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{0,-1};
	}

	@Override
	public Sender getSenders() {
		return Sender.ALL;
	}
	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
			
		if(sender instanceof Player){
			if(!RSUtils.getUser((Player)sender).hasPermission("rsutils.broadcast")){
				return;
			}
		}
		String message = args.getFT(0, args.length-1, " ");
		Bukkit.broadcastMessage(I18n.SAY.getE().write(0, sender.getName()).write(1, ChatColor.translateAlternateColorCodes('&', message)).get());
		
	}

}
