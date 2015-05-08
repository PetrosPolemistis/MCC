package mcheese.events;

import mcheese.event.Event;
import mcheese.module.Module;

public class PostModuleToggleEvent extends Event
{
	private Module module;
	
	public PostModuleToggleEvent(Module module)
	{
		this.module = module;
	}
	public Module getModule()
	{
		return this.module;
	}
}
