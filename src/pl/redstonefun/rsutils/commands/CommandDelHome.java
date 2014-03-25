package pl.redstonefun.rsutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.mysql.HomeSQL;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="delhome",description="Usuwa home'a")
public class CommandDelHome implements Command {

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
		User user = RSUtils.getUser((Player)sender);
		if(user.hasPermission("rsutils.home.delete")){
			if(HomeSQL.deleteHome(user.getName().toLowerCase(), args.get(0).toLowerCase())){
				user.sendMessage("&aUsunięto home'a");
			} else {
				user.sendMessage("&4Home nie istnieje!");
			}
			
		}
	}

}
