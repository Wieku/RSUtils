package pl.redstonefun.rsutils.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import pl.redstonefun.rsutils.api.RSListener;
import pl.redstonefun.rsutils.user.User;

@RSListener
public class PlayerDoubleBeltListener implements Listener {

	@EventHandler
	public void onPlayerClickStar(PlayerInteractEvent e){
		
		User user = new User(e.getPlayer());
		
		if(user.getItemInHand().getType() == Material.NETHER_STAR){
			
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
				e.setCancelled(true);
				if(user.hasPermission("rsutils.dbelt")){
					Inventory inv = user.getInventory();
					for(int i = 0; i <= 8; i++){
						ItemStack itm = inv.getItem(i);
						inv.setItem(i, inv.getItem(i+27));
						inv.setItem(i+27, itm);
					}
					user.updateInventory();
				}
				
			}
			
		}
		
	}
}
