package pl.redstonefun.rsutils.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pl.redstonefun.rsutils.api.RSListener;
import pl.redstonefun.rsutils.user.User;

@RSListener
public class PlayerChoseWarpListener implements Listener {
	
	@EventHandler
	public void onPlayerChose(InventoryClickEvent e){
		
		if(e.getInventory().getName().equals(ChatColor.DARK_RED + "Teleporter")){
			e.setCancelled(true);
			if(e.getInventory().contains(e.getCurrentItem())){
				new User((Player)e.getWhoClicked()).executeCommand("warp " + ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
			}			
		} else {
			ItemStack item = e.getCurrentItem();
			if(item.getType() == Material.WATCH){
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.GREEN + "Teleporter");
				item.setItemMeta(meta);
				e.setCurrentItem(item);
			}
		}
	}
}
