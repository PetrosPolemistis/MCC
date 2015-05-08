package mcheese.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;

import org.lwjgl.input.Keyboard;

import mcheese.annotations.EventTarget;
import mcheese.event.EventListener;
import mcheese.events.GameTickEvent;
import mcheese.module.Module;
import mcheese.module.ModuleCategory;

public class Jesus extends Module implements EventListener
{

	public Jesus() 
	{
		super("Jesus", Keyboard.KEY_NONE, ModuleCategory.PLAYER);
	}

	@Override
	public void onToggle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnable() 
	{
		final EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		
		new Thread() {
			@Override
			public void run()
			{
				if(!Jesus.this.isToggled())
				{
					return;
				}
				if(player.isInWater() || player.handleLavaMovement())
				{
					if(player.motionY <= 0.0D)
					{
						player.motionY = 0.01D;
					}
					player.motionX *= 1.0D;
					player.motionZ *= 1.0D;
				}
				this.restart();
			}
			public void restart()
			{
				try {
					Thread.sleep(20);
					this.run();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}
}
