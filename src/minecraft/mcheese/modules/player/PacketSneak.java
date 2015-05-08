package mcheese.modules.player;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C0BPacketEntityAction;

import org.lwjgl.input.Keyboard;

import mcheese.MCheese;
import mcheese.annotations.EventTarget;
import mcheese.event.EventListener;
import mcheese.events.PacketSendEvent;
import mcheese.events.PostMotionUpdateEvent;
import mcheese.events.MotionUpdateEvent;
import mcheese.events.PostPacketSendEvent;
import mcheese.events.UpdateEvent;
import mcheese.hooks.EntityClientPlayerMPHook;
import mcheese.module.Module;
import mcheese.module.ModuleCategory;

public class PacketSneak extends Module implements EventListener
{
	
	public PacketSneak()
	{
		super("PacketSneak", Keyboard.KEY_N, ModuleCategory.PLAYER);
	}

	@Override
	public void onToggle() 
	{
	}

	@Override
	public void onEnable() 
	{
		EntityClientPlayerMP player = MCheese.MC.thePlayer;
		player.sendQueue.addToSendQueue(new C0BPacketEntityAction(player, 1));
	}
	@Override
	public void onDisable() 
	{
		EntityClientPlayerMP player = MCheese.MC.thePlayer;
		player.sendQueue.addToSendQueue(new C0BPacketEntityAction(player, 2));
	}
	@EventTarget
	public void onPreMotionUpdate(MotionUpdateEvent event)
	{
		EntityClientPlayerMP player = MCheese.MC.thePlayer;
		player.sendQueue.addToSendQueue(new C0BPacketEntityAction(player, 2));
	}
	@EventTarget
	public void onPostMotionUpdate(PostMotionUpdateEvent event)
	{
		EntityClientPlayerMP player = MCheese.MC.thePlayer;
		player.sendQueue.addToSendQueue(new C0BPacketEntityAction(player, 1));
	}
	/*@EventTarget
	public void onPacketSend(PacketSendEvent event)
	{
		EntityClientPlayerMP player = MCheese.MC.thePlayer;
		Packet pack = event.getOutboundPacket();
		
		if(pack instanceof C02PacketUseEntity)
		{
			player.sendQueue.addToSendQueue(new C0BPacketEntityAction(player, 2));
		}
	}
	@EventTarget
	public void onPostPacketSend(PostPacketSendEvent event)
	{
		EntityClientPlayerMP player = MCheese.MC.thePlayer;
		Packet pack = event.getPacket();
		
		if(pack instanceof C02PacketUseEntity)
		{
			player.sendQueue.addToSendQueue(new C0BPacketEntityAction(player, 1));
		}
	}*/
}
