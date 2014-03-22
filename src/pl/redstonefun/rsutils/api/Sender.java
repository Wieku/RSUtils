package pl.redstonefun.rsutils.api;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Sender {
	
	
	public static Sender PLAYER = new Sender(Player.class);
	public static Sender CONSOLE = new Sender(ConsoleCommandSender.class);
	public static Sender BLOCK = new Sender(BlockCommandSender.class);
	public static Sender ALL = new Sender(PLAYER,CONSOLE,BLOCK);
	Sender[] senders;
	
	protected Class<?> clas;
	
	protected Sender(Class<?> clas){
		this.clas = clas;
		senders = new Sender[]{this};
	}
	
	public Sender(Sender... senders){
		this.senders = senders;
	}
	
	public boolean can(CommandSender sender){
		for(Sender i : senders){
			if(i.clas.isInstance(sender)){
				return true;
			}
		}		
		return false;
	}
	
}
