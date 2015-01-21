package pl.redstonefun.rsutils.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="filter")
public class CommandFilter implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{0,0};
	}

	@Override
	public Sender getSenders() {
		return Sender.PLAYER;
	}

	@Override
	public void exec(CommandSender sender, String command, Arguments args) {

		User user = RSUtils.getUser((Player)sender);
		
		if(user.hasPermission("rsutils.filter")){
			int cmdedi = 0;
			Selection sel = user.getSelection();
			if(sel == null){
				user.sendMessage(ChatColor.RED+"Najpierw zaznacz teren!");
				return;
			}
			World w = sel.getWorld();
			Location min = sel.getMinimumPoint(), max = sel.getMaximumPoint();
			user.sendMessage(ChatColor.GREEN + "Skanowanie");
			List<CommandBlock> commandBlocks = new ArrayList<CommandBlock>();
			HashMap<String, Sign> signs = new HashMap<String, Sign>();
			for(int x=min.getBlockX();x<=max.getBlockX();x++){
				for(int y=min.getBlockY();y<=max.getBlockY();y++){
					for(int z=min.getBlockZ();z<=max.getBlockZ();z++){
						Block block = w.getBlockAt(x, y, z);
						if(block.getType() == Material.COMMAND){
							commandBlocks.add((CommandBlock)block.getState());	
						} else if(block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST){
							Sign sign = (Sign) block.getState();
							if(sign.getLine(0).startsWith("*")){
								signs.put(sign.getLine(0), sign);
							}
						}
					}
				}
			}
			
			for(CommandBlock cmdblock : commandBlocks){
				String comman = cmdblock.getCommand();
				String[] comm = comman.split(" ");
				boolean edi = false;
				for(String h : comm){
					if(h.startsWith("&")){       			
						Sign sgn = signs.get(h.replace("&", "*"));
						if (sgn != null){
							edi = true;
							int X = sgn.getX() - cmdblock.getX();
							int Y = sgn.getY() - cmdblock.getY();
							int Z = sgn.getZ() - cmdblock.getZ();
							comman = comman.replaceAll(h, "~"+ X + " ~" + Y + " ~" + Z);
							cmdblock.setCommand(comman);
							cmdblock.update();
						}
					}
				}
				
				if(edi){
					++cmdedi;
				}
				
			}
			for(Sign sig : signs.values()){
				sig.getBlock().setType(Material.AIR);
			}
			user.sendMessage(ChatColor.GREEN + "Edytowano " + cmdedi + " Command " + (cmdedi==1?"Block":(cmdedi==2?"Blocki":"Blocków")));
		}
	}
}
