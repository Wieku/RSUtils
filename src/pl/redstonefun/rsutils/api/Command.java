package pl.redstonefun.rsutils.api;

import org.bukkit.command.CommandSender;

public interface Command {
	
	public int[] getMinMax();
	public Sender getSenders();
	public void exec(CommandSender sender, String command, Arguments args);
	
}
