package pl.redstonefun.rsutils.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pl.redstonefun.rsutils.api.Arguments;
import pl.redstonefun.rsutils.api.Command;
import pl.redstonefun.rsutils.api.RSCommand;
import pl.redstonefun.rsutils.api.Sender;
import pl.redstonefun.rsutils.user.User;

@RSCommand(command="item",description="Tworzy ci item.", aliases={"i"})
public class CommandItem implements Command {

	@Override
	public int[] getMinMax() {
		return new int[]{1, 3};
	}

	@Override
	public Sender getSenders() {
		return Sender.PLAYER;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void exec(CommandSender sender, String command, Arguments args) {
		/*User user = new User((Player)sender);
		if(user.hasPermission("rsutils.item")){
			
			Material material;
			
			String materialType;
			short data = 0;
			
			if(args[0].contains(":")){
				String[] g = args[0].split(":");
				materialType = g[0];
				try {
					data = Short.parseShort(g[1]);
				} catch(Exception e){
					user.sendMessage("&4 Błędne dane przedmiotu!");
					return;
				}
			} else {
				materialType = args[0];
			}
			
			
			
			try {
				material = Material.getMaterial(Integer.parseInt(materialType));
			} catch(NumberFormatException e){
				material = Material.getMaterial(materialType.toUpperCase());
			}
			
			if(material == null){
				user.sendMessage("&4 Nie ma takiego przedmiotu!");
				return;
			}
			
			ItemStack item = new ItemStack(material);
			
			String metaData = null;
			
			int count = 0;
			
			if(args.length == 2){
				try {
					count = Integer.parseInt(args[1]);
				} catch(NumberFormatException e){
					metaData = args[1];
				}
			}
			
			if(args.length == 3){
				if(metaData == null){
					metaData = args[2];
				}
			}
			
			item.setDurability(data);
			item.setAmount(count);
			ItemMeta f = item.getItemMeta();
			f.setLore(Arrays.asList("edfeufheufhue","dgvhfgvhfgvhfvgbhfvbghbhvf"));
			//System.out.println(f.serialize().put);
			//ConfigurationSerialization.
			//item.setItemMeta((ItemMeta)ConfigurationSerialization.deserializeObject(new Object()));
			//System.out.println(item.toString());
		}*/
	}	
	
	public Map<String, Object> serialize(String str){
		Map<String, Object> blah = new HashMap<String, Object>();
		
		//for(String)
		
		return blah;
	}
	
	
}
