package mcheese.modules.combat;

import net.minecraft.client.entity.EntityClientPlayerMP;

import org.lwjgl.input.Keyboard;

import mcheese.MCheese;
import mcheese.annotations.EventTarget;
import mcheese.event.EventListener;
import mcheese.events.UpdateEvent;
import mcheese.events.VelocityEvent;
import mcheese.module.Module;
import mcheese.module.ModuleCategory;

public class AntiKnockback extends Module implements EventListener
{

	private double motionX = 0.0D;
	private double motionY = 0.0D;
	private double motionZ = 0.0D;
	
	public AntiKnockback()
	{
		super("AntiKnockback", Keyboard.KEY_NONE, ModuleCategory.COMBAT);
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
	public void onVelocity(VelocityEvent event)
	{
		event.setCancelled(true);
	}
}
