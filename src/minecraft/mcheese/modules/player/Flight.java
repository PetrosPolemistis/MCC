package mcheese.modules.player;

import org.lwjgl.input.Keyboard;

import mcheese.MCheese;
import mcheese.annotations.EventTarget;
import mcheese.event.EventListener;
import mcheese.events.UpdateEvent;
import mcheese.module.ModuleCategory;
import mcheese.module.Module;

public class Flight extends Module implements EventListener
{
	public Flight()
	{
		super("Flight", Keyboard.KEY_NONE, ModuleCategory.PLAYER);
	}

	@Override
	public void onToggle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDisable() 
	{
		MCheese.MC.thePlayer.capabilities.isFlying = false;
	}
	@EventTarget
	public void onPlayerUpdate(UpdateEvent event) 
	{
		if(!this.isToggled())
		{
			return;
		}
		MCheese.MC.thePlayer.capabilities.isFlying = true;
	}

}
