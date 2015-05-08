package mcheese.events;

import net.minecraft.entity.Entity;
import mcheese.event.Cancellable;
import mcheese.event.Event;

public class AttackEntityEvent extends Event implements Cancellable
{
	private Entity entity;
	
	private boolean cancelled = false;

	public AttackEntityEvent(Entity entity)
	{
		this.entity = entity;
	}
	public Entity getEntity()
	{
		return this.entity;
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
