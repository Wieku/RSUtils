package pl.redstonefun.rsutils.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;

@RSCommand(command="ping", description="Pong!")
public class CommandPing implements Command {

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		sender.sendMessage(ChatColor.AQUA + "Pong!");
	}

}
