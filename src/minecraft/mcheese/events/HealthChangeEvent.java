package mcheese.events;

import mcheese.event.Cancellable;
import mcheese.event.Event;

public class HealthChangeEvent extends Event implements Cancellable
{
	
	float health;
	
	private boolean cancelled = false;

	public HealthChangeEvent(float health)
	{
		this.health = health;
	}
	public float getHealth()
	{
		return this.health;
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
