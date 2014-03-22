package pl.redstonefun.rsutils.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.user.User;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

@RSCommand(command="setrank", description="Ustawia rangę dla gracza", aliases={"sr"})
public class CommandSetRank implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{2,2};
	}
	
	@Override
	public Sender getSenders() {
		return Sender.ALL;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		
		if(sender instanceof Player){
			if(!new User((Player)sender).hasPermission("rsutils.setrank")){
				return;
			}
		}
		
		PermissionUser user = PermissionsEx.getUser(args.get(0));
		
		user.setGroups(new String[]{args.get(1)});
		
		User player = RSUtils.getUser(args.get(0));
		
		if(player != null){
			player.updateListName();
		}
		
		sender.sendMessage(ChatColor.GREEN + "Zmieniono rangę!");
		
	}

}
