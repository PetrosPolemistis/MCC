package mcheese.modules.player;

import org.lwjgl.input.Keyboard;

import mcheese.MCheese;
import mcheese.annotations.EventTarget;
import mcheese.event.EventListener;
import mcheese.events.UpdateEvent;
import mcheese.module.Module;
import mcheese.module.ModuleCategory;

public class Sneak extends Module implements EventListener
{

	public Sneak()
	{
		super("Sneak", Keyboard.KEY_NONE, ModuleCategory.PLAYER);
	}

	@Override
	public void onToggle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnable() {
		
	}

	@Override
	public void onDisable() 
	{
		MCheese.MC.gameSettings.keyBindSneak.pressed = false;
	}
	@EventTarget
	public void onPlayerUpdate(UpdateEvent event)
	{
		if(!this.isToggled())
		{
			return;
		}
		MCheese.MC.gameSettings.keyBindSneak.pressed = true;
	}
}
