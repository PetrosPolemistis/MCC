package mcheese.events;

import mcheese.event.Cancellable;
import mcheese.event.Event;

public class ChatReceiveEvent extends Event implements Cancellable
{
	
	private String message;
	
	private boolean cancelled = false;

	public ChatReceiveEvent(String message)
	{
		this.message = message;
	}
	public String getMessage()
	{
		return this.message;
	}
	public void setMessage(String message)
	{
		this.message = message;
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
