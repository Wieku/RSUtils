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
import pl.redstonefun.rsutils.message.I18n;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.yaml.YAML;

@RSCommand(command = "ban", description="Banuje gracza")
public class CommandBan implements Command{

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
			
		if(sender instanceof Player){
			if(!RSUtils.getUser((Player)sender).hasPermission("rsutils.ban")){
				return;
			}
		}
						
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args.get(0));
				
		String reason = "Admin ma zawsze rację!";
				
		if(args.length > 1){
			reason = args.getFT(1, args.length-1, " ");
		}

		Bukkit.getBanList(Type.NAME).addBan(offlinePlayer.getName(), reason, null, sender.getName());

		if(offlinePlayer.isOnline()) offlinePlayer.getPlayer().kickPlayer(I18n.UBANNED.getE().write(0, reason).get());
		Bukkit.broadcastMessage(I18n.USBANNED.getE().write(0, offlinePlayer.getName()).write(1, reason).get());
	}

	@Override
	public int[] getMinMax() {
		return new int[]{1,-1};
	}

	@Override
	public Sender getSenders() {
		return new Sender(Sender.PLAYER, Sender.CONSOLE);
	}

}
