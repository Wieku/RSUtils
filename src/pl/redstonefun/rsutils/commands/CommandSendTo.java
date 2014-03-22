package pl.redstonefun.rsutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.message.I18n;

@RSCommand(command="sendto", description="Wysyła wiadomość do gracza bez [@]")
public class CommandSendTo implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{1,-1};
	}

	@Override
	public Sender getSenders() {
		return Sender.BLOCK;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		
		Player player = Bukkit.getPlayer(args.get(0));
		
		if(player != null){
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', args.getFT(1, args.length-1, " ")));
		} else {
			sender.sendMessage(I18n.UOFF.get());
		}
		
	}

}