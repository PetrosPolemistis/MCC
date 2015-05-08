package mcheese.events;

import mcheese.event.Cancellable;
import mcheese.event.Event;

public class ChatSendEvent extends Event implements Cancellable
{

	private String message;
	
	private boolean cancelled = false;
	
	public ChatSendEvent(String message)
	{
		this.message = message;
	}
	public String getMessage()
	{
		return this.message;
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
