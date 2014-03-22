package pl.redstonefun.rsutils.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;

@RSCommand(command="ping", description="Pong!")
public class CommandPing implements Command {

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		sender.sendMessage(ChatColor.AQUA + "Pong!");
	}

	@Override
	public int[] getMinMax() {
		return new int[]{0,0};
	}

	@Override
	public Sender getSenders() {
		return Sender.ALL;
	}
}
