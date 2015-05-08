package mcheese.events;

import java.util.Queue;

import net.minecraft.network.INetHandler;

import mcheese.event.Cancellable;
import mcheese.event.Event;

public class PacketProcessEvent extends Event implements Cancellable
{
	private Queue packet_queue;
	private INetHandler nethandler;
	
	private boolean cancelled = false;
		
	public PacketProcessEvent(Queue packet_queue, INetHandler nethandler)
	{
		this.packet_queue = packet_queue;
		this.nethandler = nethandler;
	}
	public Queue getPacketQueue()
	{
		return this.packet_queue;
	}
	public INetHandler getNetHandler()
	{
		return this.nethandler;
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
