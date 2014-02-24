package pl.redstonefun.rsutils.user;

import org.bukkit.craftbukkit.v1_7_R1.CraftServer;
import org.bukkit.craftbukkit.v1_7_R1.block.CraftBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import net.minecraft.server.v1_7_R1.EntityPlayer;
import net.minecraft.server.v1_7_R1.MinecraftServer;
import net.minecraft.server.v1_7_R1.NetworkManager;
import net.minecraft.server.v1_7_R1.PacketPlayInUpdateSign;
import net.minecraft.server.v1_7_R1.PlayerConnection;
import net.minecraft.server.v1_7_R1.SharedConstants;
import net.minecraft.server.v1_7_R1.TileEntity;
import net.minecraft.server.v1_7_R1.TileEntitySign;
import net.minecraft.server.v1_7_R1.WorldServer;

public class PlayerConnectionE extends PlayerConnection {

	public MinecraftServer minecraftServer;
	private CraftServer server;
	private static int j;
	
	public PlayerConnectionE(MinecraftServer minecraftserver,
			NetworkManager networkmanager, EntityPlayer entityplayer) {
		super(minecraftserver, networkmanager, entityplayer);
		minecraftServer = minecraftserver;
		server = minecraftserver.server;
	}
	
	@Override
	public void a(PacketPlayInUpdateSign packetplayinupdatesign) {
		if (this.player.dead) return;

	    this.player.w();
	    WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);

	    if (worldserver.isLoaded(packetplayinupdatesign.c(), packetplayinupdatesign.d(), packetplayinupdatesign.e())) {
	      TileEntity tileentity = worldserver.getTileEntity(packetplayinupdatesign.c(), packetplayinupdatesign.d(), packetplayinupdatesign.e());

	      for (int j = 0; j < 4; j++) {
	        boolean flag = true;

	        if (packetplayinupdatesign.f()[j].length() > 15)
	          flag = false;
	        else {
	          for (int i = 0; i < packetplayinupdatesign.f()[j].length(); i++) {
	            if (!SharedConstants.isAllowedChatCharacter(packetplayinupdatesign.f()[j].charAt(i))) {
	              flag = false;
	            }
	          }
	        }

	        if (!flag) {
	          packetplayinupdatesign.f()[j] = "!?";
	        }
	      }

	      if ((tileentity instanceof TileEntitySign)) {
	        j = packetplayinupdatesign.c();
	        int k = packetplayinupdatesign.d();

	        int i = packetplayinupdatesign.e();
	        TileEntitySign tileentitysign1 = (TileEntitySign)tileentity;

	        Player player = this.server.getPlayer(this.player);
	        SignChangeEvent event = new SignChangeEvent((CraftBlock)player.getWorld().getBlockAt(j, k, i), this.server.getPlayer(this.player), packetplayinupdatesign.f());
	        this.server.getPluginManager().callEvent(event);

	        if (!event.isCancelled()) {
	          for (int l = 0; l < 4; l++) {
	            tileentitysign1.lines[l] = event.getLine(l);
	            if (tileentitysign1.lines[l] == null) {
	              tileentitysign1.lines[l] = "";
	            }
	          }
	          tileentitysign1.isEditable = false;
	        }

	        tileentitysign1.update();
	        worldserver.notify(j, k, i);
	      }
	    }
	}
	
}
