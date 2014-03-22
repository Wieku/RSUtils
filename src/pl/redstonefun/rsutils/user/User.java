package pl.redstonefun.rsutils.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import pl.redstonefun.rsutils.message.I18n;
import pl.redstonefun.rsutils.message.Messages;
import pl.redstonefun.rsutils.warp.Warp;
import pl.redstonefun.rsutils.yaml.YAML;
import pl.redstonefun.rsutils.yaml.YAML.type;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class User {
	
	protected Player player;
	protected PermissionUser user;
	protected static HashMap<Player, Location> lastLocation = new HashMap<Player, Location>();
	protected static List<Player> afkPlayers = new ArrayList<Player>();
	protected static List<Player> vanishedPlayers = new ArrayList<Player>();
	protected static HashMap<Player, Long> lastActivity = new HashMap<Player, Long>();
	protected static HashMap<Player, Player> lastMessageSender = new HashMap<Player, Player>();
	protected static WorldGuardPlugin WG = WGBukkit.getPlugin();
	protected static WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
	
	public User(Player player){
		this.player = player;
		if(player != null)
			user = PermissionsEx.getUser(player);
	}
	
	/**
	 * Checks if player can build here
	 * @param loc
	 * @return boolean
	 */
	public boolean canBuildHere(Location loc){
		if(hasPermission("rsutils.build.everywhere") || player.isOp()){
			return true;
		} else {
			return WG.canBuild(player, loc);
		}
	}
	
	/**
	 * Clears inventory
	 */
	public void clearInventory(){
		player.getInventory().clear();
	}

	public void executeCommand(String command){
		player.performCommand(command);
	}
	
	/**
	 * Get Bukkit Player's instance
	 * @return Player's instance
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Get player's name with PermissionsEx prefix
	 * @return Name with prefix
	 */
	public String getColoredName(){
		return ChatColor.translateAlternateColorCodes('&', user.getPrefix() + getName());
	}
	
	/**
	 * Get chat format for User
	 * @return Chat format
	 */
	public String getChatFormat(){
		String format = YAML.getString(type.CONFIG, "format." + getGroup());
		if(format != null){
			format = format.replace("{NICK}", getColoredName());
		} else {
			format = getColoredName() + "&f: {MESSAGE}";
		}
		return ChatColor.translateAlternateColorCodes('&', format);
	}
	
	/**
	 * @return PermissionsEx User group
	 */
	public String getGroup(){
		return (user.getGroups()[0]).getName();
	}
	
	public ItemStack getHat(){
		return player.getInventory().getHelmet();
	}
	
	public ItemStack getItemInHand(){
		return player.getItemInHand();
	}
	
	public Inventory getInventory() {
		return player.getInventory();
	}
	
	public String getName(){
		return player.getName();
	}
	
	public long getLastActivity(){
		return lastActivity.get(getPlayer());
	}
	
	public Location getLocation(){
		return player.getLocation();
	}
	
	public String getListName(){
		return getPlayer().getPlayerListName();
	}
	
	public int getRank(){
		return (user.getGroups()[0]).getRank();
	}
	
	public Selection getSelection(){
		return we.getSelection(player);
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
	
	public boolean isAfk(){
		return afkPlayers.contains(getPlayer());
	}
	
	public boolean isOnline(){
		if(player == null || !player.isOnline()){
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isFlying(){
		return player.isFlying();
	}
	
	public boolean isVanished(){
		return vanishedPlayers.contains(getPlayer());
	}
	
	public void jump(double height, int speed){
		Location location = player.getLocation();
		location.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 200);
		location.getWorld().playSound(location, Sound.GHAST_FIREBALL, 1.0F, 1.0F);
		player.setVelocity(location.getDirection().setY(height).multiply(speed));
	}
	

	public void kick(String string) {
		player.kickPlayer(string);	
	}
	
	public void kill(){
		player.setHealth(0.0D);
	}
	
	public void login(){
		updateLastActivity();
		updateListName();
	}
	
	public void left(){
		lastActivity.remove(getPlayer());
		afkPlayers.remove(getPlayer());
	}
	
	public String registerLast(boolean notify){
		if(hasPermissionSilent("rsutils.back")){
			lastLocation.put(getPlayer(), player.getLocation());
			return (notify?"Wpisz /back by tutaj powrócić":"");
		} else {
			return "";
		}
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
	
	public void sendMessage(String message){
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
		
	public void sendPrivateMessage(User forWho, String message){
		sendMessage(Messages.msgToHim.replace("%user", forWho.getColoredName()) + message);
		forWho.sendMessage(Messages.msgToMe.replace("%user", getColoredName()) + message);
		lastMessageSender.put(forWho.getPlayer(), getPlayer());
	}
	
	public void setAfk(boolean state){
		if(state){
			afkPlayers.add(getPlayer());
			Bukkit.broadcastMessage(I18n.UAFK.getE().write(0, getColoredName()).get());
		} else {
			updateLastActivity();
			afkPlayers.remove(getPlayer());
			Bukkit.broadcastMessage(I18n.UNOAFK.getE().write(0, getColoredName()).get());
		}
	}
	
	public void setHat(ItemStack itemstack){
		player.getInventory().setHelmet(itemstack);
	}
	
	public void setItemInHand(ItemStack stack){
		player.setItemInHand(stack);
	}

	public void setFlySpeed(float speed){
		player.setFlySpeed(speed);
	}
	
	public void setVanished(boolean bool){
		if(bool){
			vanishedPlayers.add(getPlayer());
			for(Player pl: Bukkit.getOnlinePlayers()){
					pl.hidePlayer(getPlayer());	
					System.out.println(pl.canSee(getPlayer()));

			}
		} else {
			vanishedPlayers.remove(getPlayer());
			for(Player pl: Bukkit.getOnlinePlayers()){
					pl.showPlayer(getPlayer());
					System.out.println(pl.canSee(getPlayer()));
			}
		}
		
	}
	
	public void setWalkSpeed(float speed){
		player.setWalkSpeed(speed);
	}
	
	public void teleport(User user){
		teleport(user.getPlayer());
	}
	
	public void teleport(Player player) {
		sendMessage(Messages.teleport.replace("%loc", player.getName()) + registerLast(true));
		player.sendMessage(ChatColor.GREEN + getColoredName() + ChatColor.RESET + ChatColor.GREEN + " teleportuje się do Ciebie");
		this.player.teleport(player);
	}

	public void teleport(World world){
		Location location = world.getSpawnLocation();
		location.add(0.5D, 0.0D, 0.5D);
		sendMessage(Messages.teleport.replace("%loc", world.getName()) + registerLast(true));
		teleport(location);
	}
	
	public void teleport(Warp warp){
		sendMessage(Messages.teleport.replace("%loc", warp.getName()) + registerLast(true));
		teleport(warp.toLocation());
	}
	
	public void teleport(Location loc){
		World world = getLocation().getWorld();
		for(int i=0;i<10;i++)world.playEffect(getLocation(), Effect.ENDER_SIGNAL, 200);
		world.playSound(getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
		player.teleport(loc);
	}
	
	public void teleportToLast(){
		Location loc = lastLocation.get(getPlayer());
		if(loc != null){
			sendMessage(Messages.teleport.replace("%loc", "ostatniej lokacji"));
			registerLast(false);
			teleport(loc);
		} else {
			sendMessage("&4Nie posiadasz ostatniej lokacji!");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void updateInventory(){
		player.updateInventory();
	}
	
	public void updateLastActivity(){
		lastActivity.put(getPlayer(), System.currentTimeMillis());
	}
	
	public void updateListName(){
		String nick = getColoredName();
		if(nick.length() > 16){
			player.setPlayerListName(nick.substring(0,16));
		} else {
			player.setPlayerListName(nick);
		}
	}
	
}
