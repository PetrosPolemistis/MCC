package mcheese.modules.tools;

import io.netty.util.concurrent.GenericFutureListener;

import java.io.IOException;
import java.net.InetAddress;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.util.IChatComponent;

import org.lwjgl.input.Keyboard;

import mcheese.MCheese;
import mcheese.annotations.EventTarget;
import mcheese.event.EventListener;
import mcheese.events.PacketProcessEvent;
import mcheese.events.PacketSendEvent;
import mcheese.hooks.NetworkManagerHook;
import mcheese.module.Module;
import mcheese.module.ModuleCategory;

public class Ping extends Module implements EventListener
{

	public Ping() 
	{
		super("Ping", Keyboard.KEY_NONE, ModuleCategory.TOOLS);
	}

	@Override
	public void onToggle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		MCheese.MC.theWorld.sendQuittingDisconnectingPacket();
		try {
			final String server_ip = "play.itsjerryandharry.com";
			
			InetAddress server_address = InetAddress.getByName(server_ip);
			NetworkManagerHook netman = NetworkManagerHook.provideLanClient(server_address, 25565);
			netman.setNetHandler(new INetHandlerStatusClient() {
				
				@Override
				public void onNetworkTick() {
					
				}
				
				@Override
				public void onDisconnect(IChatComponent p_147231_1_) {
					
				}
				
				@Override
				public void onConnectionStateTransition(EnumConnectionState p_147232_1_,
						EnumConnectionState p_147232_2_) {
					
				}
				
				@Override
				public void handleServerInfo(S00PacketServerInfo p_147397_1_) {
					System.out.println(p_147397_1_.func_149294_c().func_151317_a().getUnformattedText());
				}
				
				@Override
				public void handlePong(S01PacketPong p_147398_1_) {
					System.out.println("PONG RECEIVED!");
				}
			});
			netman.scheduleOutboundPacket(new C00Handshake(5, server_ip, 25565, EnumConnectionState.STATUS), new GenericFutureListener[0]);
			netman.scheduleOutboundPacket(new C00PacketServerQuery(), new GenericFutureListener[0]);
			netman.scheduleOutboundPacket(new C01PacketPing(), new GenericFutureListener[0]);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}
}
