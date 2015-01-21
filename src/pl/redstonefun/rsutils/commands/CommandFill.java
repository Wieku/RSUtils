package pl.redstonefun.rsutils.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;

@RSCommand(command="fill")
public class CommandFill implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{7,8};
	}

	@Override
	public Sender getSenders() {
		return Sender.BLOCK;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		
		int[] cordfromloc = new int[6];
		int[] cords = new int[6];
		
		Location loc = (((BlockCommandSender) sender).getBlock()).getLocation();

		World world = loc.getWorld();
		cordfromloc[0] = loc.getBlockX();
		cordfromloc[1] = loc.getBlockY();
		cordfromloc[2] = loc.getBlockZ();
		cordfromloc[3] = loc.getBlockX();
		cordfromloc[4] = loc.getBlockY();
		cordfromloc[5] = loc.getBlockZ();
		
		
		for(int i=0;i<=5;i++){
			try{
				cords[i] = args.get(i).startsWith("~")?(cordfromloc[i]+Integer.parseInt(args.get(i).split("~")[1])):Integer.parseInt(args.get(i));
			} catch (NumberFormatException t){
				return;
			}
		}
		
		int minX,minY,minZ,maxX,maxY,maxZ;
		
		if(cords[0] < cords[3]){
			minX = cords[0];
			maxX = cords[3];
		} else {
			minX = cords[3];
			maxX = cords[0];
		}
		
		if(cords[1] < cords[4]){
			minY = cords[1];
			maxY = cords[4];
		} else {
			minY = cords[4];
			maxY = cords[1];
		}
		
		if(cords[2] < cords[5]){
			minZ = cords[2];
			maxZ = cords[5];
		} else {
			minZ = cords[5];
			maxZ = cords[2];
		}
		
		for(int x=minX;x<=maxX;x++){
			for(int y=minY;y<=maxY;y++){
				for(int z=minZ;z<=maxZ;z++){
					
					try {
						
						Location location = new Location(world, x, y, z);
						Block block = world.getBlockAt(location);
						
						try {
							int s = Integer.parseInt(args.get(6));
							block.setTypeId(s);
							block.setData((byte) 0 );
							if (args.length == 8){
								try{
									block.setData(Byte.parseByte(args.get(7)));
								} catch (NumberFormatException e){}
							}
						} catch (NumberFormatException r){					
							block.setType(Material.getMaterial(args.get(6).toUpperCase()));
							block.setData((byte) 0 );
							if (args.length == 8){
								try{
									block.setData(Byte.parseByte(args.get(7)));
								} catch (NumberFormatException e){
									return;
								}
							}
						}
							
					} catch (NullPointerException e){
						return;
					}
					
					
				}
			}
		}
		
	}

}
