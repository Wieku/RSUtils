package pl.redstonefun.rsutils.listeners;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pl.redstonefun.rsutils.api.RSListener;
import pl.redstonefun.rsutils.user.User;
import pl.redstonefun.rsutils.yaml.YAML;

@RSListener
public class PlayerOpenTeleporterListener implements Listener {
	
	
	@EventHandler
	public void openedTeleporter(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getPlayer().getItemInHand().getType() == Material.WATCH){
				e.setCancelled(true);
				
				User user = new User(e.getPlayer());
				
				if(user.hasPermission("rsutils.warp.teleport")){
					YAML.type tp = YAML.type.WARPS;
					
					
					HashMap<String, String> cwarps = new HashMap<String, String>();
					
					
					
					
					for(String key : YAML.getMainKeys(tp)){
						String bool = YAML.getString(tp, key + ".cwarp");
						if(bool != null && bool.equals("true")){
							String desc = YAML.getString(tp, key + ".cdescription");
							cwarps.put(key, (desc!=null?ChatColor.translateAlternateColorCodes('&', desc):""));
						}				
					}
					
					
					Random random = new Random();

					Inventory inv = Bukkit.createInventory(user.getPlayer(), extend(cwarps.size()) , ChatColor.DARK_RED + "Teleporter");
					inv.setMaxStackSize(1);	
					System.out.println("");
					for(String name : cwarps.keySet()){
						
						Material randomResult;
						
						do {
							
							randomResult = Material.values()[random.nextInt(Material.values().length - 1)];
							
						} while (randomResult.isTransparent() || !randomResult.isBlock() || randomResult == Material.GLOWING_REDSTONE_ORE 
								|| randomResult == Material.PISTON_MOVING_PIECE || randomResult == Material.BED_BLOCK || randomResult == Material.SIGN_POST
								|| randomResult == Material.CAKE_BLOCK || randomResult == Material.BREWING_STAND || randomResult == Material.CAULDRON
								|| randomResult == Material.WOODEN_DOOR || randomResult == Material.IRON_DOOR || randomResult == Material.WALL_SIGN 
								|| randomResult == Material.PISTON_EXTENSION || randomResult == Material.STATIONARY_LAVA || randomResult == Material.STATIONARY_WATER
								|| randomResult == Material.REDSTONE_LAMP_ON);
						
						ItemStack item = new ItemStack(randomResult);
						System.out.println(randomResult.name());
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(ChatColor.AQUA + name);
						meta.setLore(Arrays.asList(cwarps.get(name)));
						item.setItemMeta(meta);
						
						inv.addItem(item);
						
					}
					
					user.getPlayer().openInventory(inv);
					
				}
				
			}
		}
	}
	
	public int extend(int id){

		if (id>=0 && id<=9) return 9;
		if (id>9 && id<=18) return 9*2;
		if (id>18 && id<=27) return 9*3;
		if (id>27 && id<=36) return 9*4;
		if (id>36 && id<=45) return 9*5;
		if (id>45 && id<=64) return 9*6;

		return 9*6;
	}
}
