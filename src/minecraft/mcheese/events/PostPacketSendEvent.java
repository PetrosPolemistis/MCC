package mcheese.events;

import net.minecraft.network.Packet;
import mcheese.event.Event;

public class PostPacketSendEvent extends Event
{
	private Packet packet;
	
	public PostPacketSendEvent(Packet packet)
	{
		this.packet = packet;
	}
	public Packet getPacket()
	{
		return this.packet;
	}
}
