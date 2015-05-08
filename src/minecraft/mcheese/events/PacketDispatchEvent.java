package mcheese.events;

import net.minecraft.network.Packet;
import mcheese.event.Cancellable;
import mcheese.event.Event;

public class PacketDispatchEvent extends Event implements Cancellable
{
	private Packet dispatched;
	
	private boolean cancelled = false;
	
	public PacketDispatchEvent(Packet dispatched)
	{
		this.dispatched = dispatched;
	}
	
	public Packet getDispatchedPacket()
	{
		return this.dispatched;
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
