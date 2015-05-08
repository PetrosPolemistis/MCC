package mcheese.modules.combat;

import java.util.ArrayList;
import java.util.Queue;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Queues;

import mcheese.MCheese;
import mcheese.annotations.EventTarget;
import mcheese.event.EventListener;
import mcheese.events.PacketSendEvent;
import mcheese.module.Module;
import mcheese.module.ModuleCategory;

public class Blink extends Module implements EventListener
{

	private ArrayList<Packet> delayed_packets = new ArrayList<Packet>();
	
	public Blink()
	{
		super("Blink", Keyboard.KEY_NONE, ModuleCategory.COMBAT);
	}

	@Override
	public void onToggle() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onEnable() 
	{
	}

	@Override
	public void onDisable() 
	{
		for(Packet p : this.delayed_packets)
		{
			if(p != null)
			{
				Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(p);
			}
		}
		this.delayed_packets.clear();
	}
	@EventTarget
	public void onPacketSend(PacketSendEvent event)
	{
		if(!this.isToggled())
		{
			return;
		}
		Packet pack = event.getOutboundPacket();		
		delayed_packets.add(pack);
		event.setCancelled(true);
	}

}
