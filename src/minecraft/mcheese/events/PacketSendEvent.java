package mcheese.events;

import net.minecraft.network.Packet;
import mcheese.event.Cancellable;
import mcheese.event.Event;

public class PacketSendEvent extends Event implements Cancellable
{

	private Packet outboundPacket;
	
	private boolean cancelled = false;
	
	public PacketSendEvent(Packet outboundPacket)
	{
		this.outboundPacket = outboundPacket;
	}
	public Packet getOutboundPacket()
	{
		return this.outboundPacket;
	}
	public void setOutboundPacket(Packet p)
	{
		this.outboundPacket = p;
	}
	@Override
	public boolean isCancelled() 
	{
		return this.cancelled;
	}
	@Override
	public void setCancelled(boolean cancelled) 
	{
		this.cancelled = cancelled;
	}
	
}
