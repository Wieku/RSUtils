package pl.redstonefun.rsutils.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.warp.Warp;

@RSCommand(command="tppos", description="Teleportuje na wskazane koordynaty")
public class CommandTpPos implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{3,3};
	}

	@Override
	public Sender getSenders() {
		return Sender.PLAYER;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		User user = RSUtils.getUser((Player) sender);
		if(user.hasPermission("rsutils.tp.pos")){
			Location loc = user.getLocation();
			try{
				loc.setX(Double.parseDouble(args.get(0)));
				loc.setY(Double.parseDouble(args.get(1)));
				loc.setZ(Double.parseDouble(args.get(2)));
				user.teleport(new Warp("koordynatów", loc));
			} catch(NumberFormatException e){
				user.sendMessage("&4Podaj liczbę/y!");
			}
		}
	}
}
