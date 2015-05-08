package mcheese.events;

import mcheese.event.Cancellable;
import mcheese.event.Event;

public class VelocityEvent extends Event implements Cancellable
{
	private boolean cancelled = false;

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
