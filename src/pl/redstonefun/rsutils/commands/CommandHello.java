package pl.redstonefun.rsutils.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;

@RSCommand(command="hello",description="Siemka",aliases={"he"})
public class CommandHello implements Command {

	@Override
	public void exec(CommandSender sender, String command, String[] args) {
		sender.sendMessage(ChatColor.GREEN + "Czeœæ!");

	}

}
