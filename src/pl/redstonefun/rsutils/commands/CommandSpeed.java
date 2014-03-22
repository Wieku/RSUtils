package pl.redstonefun.rsutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="speed", description="Ustawia twoją prędkość")
public class CommandSpeed implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{1,1};
	}
	
	@Override
	public Sender getSenders() {
		return Sender.PLAYER;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		User user = new User((Player)sender);
		if(user.hasPermission("rsutils.speed")){
			float flo;
			try{
				flo = Float.parseFloat(args.get(0));
			} catch(NumberFormatException e){
				user.sendMessage("&4Podaj liczbę!");
				return;
			}

			if(user.isFlying()){		
				if(flo > 10.0F || flo < 0.0F){
					user.sendMessage("&4Liczba musi być między 0.0 a 10.0!");
					return;
				}
				user.setFlySpeed(flo/10);
				user.sendMessage("&aUstawiono prędkość chodzenia na: " + flo);
			} else {	
				if(flo > 5.0F || flo < 0.0F){
					user.sendMessage("&4Liczba musi być między 0.0 a 5.0!");
					return;
				}
				user.setWalkSpeed(flo/5);
				user.sendMessage("&aUstawiono prędkość latania na: " + flo);
			}
			
		}
	}

}
