package mcheese.events;

import mcheese.event.Cancellable;
import mcheese.event.Event;
import mcheese.module.Module;

public class ModuleToggleEvent extends Event implements Cancellable
{

	private Module module;
	
	private boolean cancelled = false;
	
	public ModuleToggleEvent(Module module)
	{
		this.module = module;
	}
	public Module getModule()
	{
		return this.module;
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
