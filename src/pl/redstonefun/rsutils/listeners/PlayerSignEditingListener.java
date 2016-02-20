package pl.redstonefun.rsutils.listeners;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.HashMap;

import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.apache.commons.lang.exception.NestableRuntimeException;
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
			final WrappedChatComponent[] lines1 = event.getPacket().getChatComponentArrays().getValues().get(0);
			final String[] lines = new String[4];


			for(int i=0; i < lines1.length;i++){
				String text="";

				try {
					StringWriter writer = new StringWriter();
					unescapeJava(writer, lines1[i].getJson().substring(1, lines1[i].getJson().length()-1));
					writer.flush();
					text = writer.toString();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				lines[i] = text;
			}

			event.setCancelled(true);
			
			Bukkit.getScheduler().runTaskAsynchronously(RSUtils.instance, () -> {
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
						
						WrappedChatComponent[] lines = new WrappedChatComponent[4];
						for(int i=0;i<lines.length;i++)
							lines[i] = WrappedChatComponent.fromText(sign.getLine(i).replace('§', '&'));
						
						PacketContainer packetSign = RSUtils.pManager.createPacket(PacketType.findLegacy(133));
						PacketContainer packetEdit = RSUtils.pManager.createPacket(PacketType.findLegacy(130));

						packetSign.getBlockPositionModifier().write(0, new BlockPosition(sign.getLocation().toVector()));

			            packetEdit.getBlockPositionModifier().write(0, new BlockPosition(sign.getLocation().toVector()));
			            packetEdit.getChatComponentArrays().write(0, lines);
			           
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


	/** SOURCE: Apache Commons Lang -> StringEscapeUtils
	 * <p>Unescapes any Java literals found in the <code>String</code> to a
	 * <code>Writer</code>.</p>
	 *
	 * <p>For example, it will turn a sequence of <code>'\'</code> and
	 * <code>'n'</code> into a newline character, unless the <code>'\'</code>
	 * is preceded by another <code>'\'</code>.</p>
	 *
	 * <p>A <code>null</code> string input has no effect.</p>
	 *
	 * @param out  the <code>Writer</code> used to output unescaped characters
	 * @param str  the <code>String</code> to unescape, may be null
	 * @throws IllegalArgumentException if the Writer is <code>null</code>
	 * @throws IOException if error occurs on underlying Writer
	 */
	public static void unescapeJava(StringWriter out, String str) throws IOException {
		if (out == null) {
			throw new IllegalArgumentException("The Writer must not be null");
		}
		if (str == null) {
			return;
		}
		int sz = str.length();
		StringBuffer unicode = new StringBuffer(4);
		boolean hadSlash = false;
		boolean inUnicode = false;
		for (int i = 0; i < sz; i++) {
			char ch = str.charAt(i);
			if (inUnicode) {
				// if in unicode, then we're reading unicode
				// values in somehow
				unicode.append(ch);
				if (unicode.length() == 4) {
					// unicode now contains the four hex digits
					// which represents our unicode character
					try {
						int value = Integer.parseInt(unicode.toString(), 16);
						out.write((char) value);
						unicode.setLength(0);
						inUnicode = false;
						hadSlash = false;
					} catch (NumberFormatException nfe) {
						throw new NestableRuntimeException("Unable to parse unicode value: " + unicode, nfe);
					}
				}
				continue;
			}
			if (hadSlash) {
				// handle an escaped value
				hadSlash = false;
				switch (ch) {
					case '\\':
						out.write('\\');
						break;
					case '\'':
						out.write('\'');
						break;
					case '\"':
						out.write('"');
						break;
					case 'r':
						out.write('\r');
						break;
					case 'f':
						out.write('\f');
						break;
					case 't':
						out.write('\t');
						break;
					case 'n':
						out.write('\n');
						break;
					case 'b':
						out.write('\b');
						break;
					case 'u':
					{
						// uh-oh, we're in unicode country....
						inUnicode = true;
						break;
					}
					default :
						out.write(ch);
						break;
				}
				continue;
			} else if (ch == '\\') {
				hadSlash = true;
				continue;
			}
			out.write(ch);
		}
		if (hadSlash) {
			// then we're in the weird case of a \ at the end of the
			// string, let's output it anyway.
			out.write('\\');
		}
	}

}
