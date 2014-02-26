package pl.redstonefun.rsutils.api;

import org.bukkit.command.CommandSender;

public interface Command {
	
	public int getMin();
	public int getMax();
	
	public void exec(CommandSender sender, String command, String[] args);
	
}
