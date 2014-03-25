package pl.redstonefun.rsutils.commands;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.message.I18n;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="gamemode", description="Zmienia tryb gry dla gracza", aliases={"gm"})
public class CommandGameMode implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{1,2};
	}

	@Override
	public Sender getSenders() {
		return Sender.ALL;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		
		GameMode gm = parse(args.get(0));
		
		if(args.length == 1){
			if(!(sender instanceof Player)){
				sender.sendMessage(I18n.WRONGARGS.get());
				return;
			}
			User user = RSUtils.getUser((Player)sender);
			if(user.hasPermission("rsutils.gm")){
				if(gm != null){
					user.setGamemode(gm);
				} else {
					user.sendMessage(I18n.INCORRECTARG.get());

				}
			}
		} else {
			if(sender instanceof Player){
				if(!RSUtils.getUser((Player)sender).hasPermission("rsutils.gm.someone")){
					return;
				}
			}
			
			User us = RSUtils.getUser(args.get(1));
			if(us != null){
				if(gm != null){
					us.setGamemode(gm);
				} else {
					sender.sendMessage(I18n.INCORRECTARG.get());
				}
			} else {
				sender.sendMessage(I18n.UOFF.get());
			}
			
		}
	}

	@SuppressWarnings("deprecation")
	public GameMode parse(String str){
		GameMode x;
			
			try {
				x = GameMode.getByValue(Integer.parseInt(str));
			} catch(NumberFormatException e){
				x = GameMode.valueOf(str);
			}
		
		return x;
	}
	
	
}
