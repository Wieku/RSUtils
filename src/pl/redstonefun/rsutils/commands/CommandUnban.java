package pl.redstonefun.rsutils.commands;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.yaml.YAML;

@RSCommand(command = "unban", description="Odbanowuje gracza", permissions = "rsutils.unban")
public class CommandUnban implements Command{

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
			
		if(sender instanceof Player){
			if(!RSUtils.getUser((Player)sender).hasPermission("rsutils.unban")){
				return;
			}
		}

		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args.get(0));

		Bukkit.getBanList(Type.NAME).pardon(offlinePlayer.getName());

		Bukkit.broadcastMessage(Messages.unBanned.replace("%user", offlinePlayer.getName()));
		
	}

	@Override
	public int[] getMinMax() {
		return new int[]{1,1};
	}
	
	@Override
	public Sender getSenders() {
		return new Sender(Sender.PLAYER,Sender.CONSOLE);
	}
}
