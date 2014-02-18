package pl.redstonefun.rsutils.api;

import org.bukkit.command.CommandSender;

public interface Command {
	
	public void exec(CommandSender sender, String command, String[] args);
	
}
