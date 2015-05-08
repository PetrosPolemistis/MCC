package mcheese.modules.combat;

import org.lwjgl.input.Keyboard;

import mcheese.MCheese;
import mcheese.annotations.EventTarget;
import mcheese.event.EventListener;
import mcheese.events.UpdateEvent;
import mcheese.module.ModuleCategory;
import mcheese.module.Module;

public class Sprint extends Module implements EventListener
{
	public Sprint() 
	{
		super("Sprint", Keyboard.KEY_H, ModuleCategory.COMBAT);
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
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}
	@EventTarget
	public void onPlayerUpdate(UpdateEvent event)
	{
		if(!this.isToggled())
		{
			return;
		}
		if((!MCheese.MC.thePlayer.isCollidedHorizontally) && (MCheese.MC.thePlayer.moveForward != 0.0F))
		{
			MCheese.MC.thePlayer.setSprinting(true);
		}
	}
}
