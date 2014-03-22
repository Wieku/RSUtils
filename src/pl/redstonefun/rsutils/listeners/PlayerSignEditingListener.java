package pl.redstonefun.rsutils.listeners;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import net.minecraft.server.v1_7_R1.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import pl.redstonefun.rsutils.main.RSUtils;
import pl.redstonefun.rsutils.user.User;

public class PlayerSignEditingListener extends PacketAdapter implements Listener {
	
	public static PlayerConnection conn;
	public static HashMap<Player, Sign> signsInEditing = new HashMap<Player, Sign>();
	
	
	public PlayerSignEditingListener(Plugin plugin){
		super(plugin, ListenerPriority.NORMAL , PacketType.Play.Client.UPDATE_SIGN);
	}
	
	@Override
	public void onPacketSending(PacketEvent event) {}
	
	@Override
	public void onPacketReceiving(PacketEvent event) {
		final Player player = event.getPlayer();
		if(signsInEditing.containsKey(player)){
			final Sign sign = signsInEditing.get(player);
			final String[] lines = event.getPacket().getStringArrays().getValues().get(0);
			event.setCancelled(true);
			
			Bukkit.getScheduler().runTaskAsynchronously(RSUtils.instance, new Runnable() {				
				@Override
				public void run() {
					SignChangeEvent ev = new SignChangeEvent(sign.getBlock(), player, lines.clone());
					Bukkit.getServer().getPluginManager().callEvent(ev);
					 if (!ev.isCancelled()) {
	                        sign.setLine(0, ev.getLine(0));
	                        sign.setLine(1, ev.getLine(1));
	                        sign.setLine(2, ev.getLine(2));
	                        sign.setLine(3, ev.getLine(3));
	                        sign.update(true);
	                        player.sendMessage(ChatColor.GREEN + "Zedytowano tabliczkę!");
	                    } else {
	                        player.sendMessage(ChatColor.DARK_RED + "Anulowano edycję tabliczki!");
	                    }
				}
			});
			signsInEditing.remove(player);
			
		}
	}
	
	@EventHandler
	public void onPlayerClickSign(PlayerInteractEvent e){
		if(e.getPlayer().isSneaking()){		
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
				Material material = e.getClickedBlock().getType();
				if(material == Material.SIGN_POST || material == Material.WALL_SIGN){
					User user = new User(e.getPlayer());
					if(user.canBuildHere(e.getClickedBlock().getLocation())){
						e.setCancelled(true);
						Sign sign = (Sign) e.getClickedBlock().getState();
						if(signsInEditing.containsValue(sign)){
							user.sendMessage("&4Już ktoś edytuje tę tabliczkę!");	
							return;
						}
						
						String[] lines = sign.getLines();
						for(int i=0;i<lines.length;i++){
							lines[i] = lines[i].replace('§', '&');
						}
						
						PacketContainer packetSign = RSUtils.pManager.createPacket(PacketType.findLegacy(133));
						PacketContainer packetEdit = RSUtils.pManager.createPacket(PacketType.findLegacy(130));
						packetSign.getIntegers().write(0, sign.getX()).write(1, sign.getY()).write(2, sign.getZ());
						
			            packetEdit.getIntegers().write(0, sign.getX()).write(1, sign.getY()).write(2, sign.getZ());
			            packetEdit.getStringArrays().write(0, lines);
			           
						try {
							RSUtils.pManager.sendServerPacket(e.getPlayer(), packetEdit);
							RSUtils.pManager.sendServerPacket(e.getPlayer(), packetSign);
							
							signsInEditing.put(e.getPlayer(), sign);
						} catch (InvocationTargetException e1) {
							user.sendMessage("&4Problem z otworzeniem edytora tabliczek!");
						}
						
					}
				}
			}
		}
	}
}
