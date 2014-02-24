package pl.redstonefun.rsutils.user;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import pl.redstonefun.rsutils.exceptions.LastLocationNotFound;
import pl.redstonefun.rsutils.message.Messages;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class User {
	
	protected Player player;
	protected PermissionUser user;
	protected static HashMap<Player, Location> lastLocation = new HashMap<Player, Location>();
	protected static HashMap<Player, Player> lastMessageSender = new HashMap<Player, Player>();
	protected static WorldGuardPlugin WG = WGBukkit.getPlugin();
	protected static WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
	
	public User(Player player){
		this.player = player;
		user = PermissionsEx.getUser(player);
	}
	
	public boolean isOnline(){
		if(player == null || !player.isOnline()){
			return false;
		} else {
			return true;
		}
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
	
	public void teleport(User user){
		teleport(user.getPlayer());
	}
	
	public void teleport(Player player) {
		sendMessage(Messages.teleport.replace("%loc", player.getName()) + registerLast(true));
		this.player.teleport(player);
	}

	public Player getPlayer() {
		return player;
	}

	public void teleport(World world){
		Location location = world.getSpawnLocation();
		location.add(0.5D, 0.0D, 0.5D);
		sendMessage(Messages.teleport.replace("%loc", world.getName()) + registerLast(true));
		teleport(location);
	}
	
	public void teleport(Location loc){
		World world = loc.getWorld();
		world.playEffect(loc, Effect.SMOKE, 200);
		world.playSound(loc, Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
		player.teleport(loc);
	}
	
	public void teleportToLast() throws LastLocationNotFound{
		Location loc = lastLocation.get(this);
		if(loc != null){
			sendMessage(Messages.teleport.replace("%loc", "ostatniej lokacji"));
			registerLast(false);
			teleport(loc);
		} else {
			throw new LastLocationNotFound();
		}
	}
	
	public String registerLast(boolean notify){
		if(hasPermissionSilent("rsutils.back")){
			lastLocation.put(getPlayer(), player.getLocation());
			return (notify?"Wpisz /back by tutaj powróciæ":"");
		} else {
			return "";
		}
	}
	
	public String getName(){
		return player.getName();
	}
	
	public String getColoredName(){
		return user.getPrefix() + getName();
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
	
	public Selection getSelection(){
		return we.getSelection(player);
	}
	
	public boolean canBuildHere(Location loc){
		if(hasPermission("rsutils.build.everywhere") || player.isOp()){
			return true;
		} else {
			return WG.canBuild(player, loc);
		}
	}
	
	public void setHat(ItemStack itemstack){
		player.getInventory().setHelmet(itemstack);
	}
	
	public void sendMessage(String message){
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public void sendPrivateMessage(User forWho, String message){
		sendMessage(Messages.msgToHim.replace("%user", forWho.getColoredName()) + message);
		forWho.sendMessage(Messages.msgToMe.replace("%user", getColoredName()) + message);
		lastMessageSender.put(forWho.getPlayer(), getPlayer());
	}
	
	public void reply(String message){
		if(lastMessageSender.containsKey(getPlayer())){
			User usr = new User(lastMessageSender.get(getPlayer()));
			if(usr.isOnline()){
				sendPrivateMessage(usr, message);
			} else {
				sendMessage(Messages.userOffline.replace("%user", usr.getColoredName()));
			}
			
		} else {
			sendMessage("&4Ostatni nadawca nie istnieje!");
		}
	}
	
}
