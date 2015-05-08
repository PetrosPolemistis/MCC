package mcheese.modules.world;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import mcheese.MCheese;
import mcheese.annotations.EventTarget;
import mcheese.event.EventListener;
import mcheese.events.RenderEvent;
import mcheese.module.Module;
import mcheese.module.ModuleCategory;

public class NameTagESP extends Module implements EventListener
{

	public NameTagESP()
	{
		super("NametagESP", Keyboard.KEY_N, ModuleCategory.WORLD);
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
	public void onRender(RenderEvent event)
	{
		if(!this.isToggled())
		{
			return;
		}
		for(Object eo : MCheese.MC.theWorld.loadedEntityList)
		{
			if(!(eo instanceof EntityPlayer))
			{
				continue;
			}
			EntityPlayer pl = (EntityPlayer) eo;
			//TODO: complete module...
		}
	}
}
