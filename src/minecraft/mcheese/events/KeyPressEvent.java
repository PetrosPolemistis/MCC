package mcheese.events;

import mcheese.event.Cancellable;
import mcheese.event.Event;

public class KeyPressEvent extends Event
{
	private int key;
	
	public KeyPressEvent(int key)
	{
		this.key = key;
	}
	public int getKey()
	{
		return this.key;
	}
}
