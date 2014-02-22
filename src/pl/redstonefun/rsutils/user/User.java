package pl.redstonefun.rsutils.user;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pl.redstonefun.rsutils.message.Messages;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class User {
	
	protected Player player;
	protected PermissionUser user;
	
	public User(Player player){
		this.player = player;
		user = PermissionsEx.getUser(player);
	}
	
	public boolean hasPermission(String permission){
		if(user.has(permission)){
			return true;
		} else {
			player.sendMessage(Messages.hasNoPermission);
			return false;
		}
	}
	
	public boolean hasPermissionSilent(String permission){
		return user.has(permission);
	}
	
	public void clearInventory(){
		player.getInventory().clear();
	}
	
	public void teleport(World world){
		Location location= world.getSpawnLocation();
		location.setX(location.getX()+0.5D);
		location.setZ(location.getZ()+0.5D);
	}
	
	public void jump(double height, int speed){
		Location location = player.getLocation();
		location.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 200);
		location.getWorld().playSound(location, Sound.GHAST_FIREBALL, 1.0F, 1.0F);
		player.setVelocity(location.getDirection().setY(height).multiply(speed));
	}
	
	public ItemStack getItemInHand(){
		return player.getItemInHand();
	}
	
	public void setItemInHand(ItemStack stack){
		player.setItemInHand(stack);
	}
	
	public void setHat(ItemStack itemstack){
		
	}
	
	public void sendMessage(String message){
		
	}
}
