package mcheese.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;

import org.lwjgl.input.Keyboard;

import mcheese.annotations.EventTarget;
import mcheese.event.EventListener;
import mcheese.events.GameTickEvent;
import mcheese.module.Module;
import mcheese.module.ModuleCategory;

public class Regen extends Module implements EventListener
{

	public Regen()
	{
		super("Regen", Keyboard.KEY_NONE, ModuleCategory.COMBAT);
	}

	@Override
	public void onToggle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnable() 
	{
		Minecraft.getMinecraft().thePlayer.motionY = 0.1D;
	}

	@Override
	public void onDisable() {
		
	}
	@EventTarget
	public void onGameTick(GameTickEvent event) 
	{
		boolean canHeal = (Minecraft.getMinecraft().thePlayer.onGround) || (Minecraft.getMinecraft().thePlayer.isInWater()) || (Minecraft.getMinecraft().thePlayer.isOnLadder());
		boolean shouldHeal = (Minecraft.getMinecraft().thePlayer.getHealth() <= 19.0F) && (Minecraft.getMinecraft().thePlayer.getFoodStats().getFoodLevel() > 17);
	    if ((canHeal) && (shouldHeal))
		{
	    	for (int s = 0; s <= 20; s++)
		    {
	    		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(Minecraft.getMinecraft().thePlayer.onGround));
		    }
		}
	}
}
