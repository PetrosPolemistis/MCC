package mcheese.hooks;

import mcheese.MCheese;
import mcheese.events.ChatSendEvent;
import mcheese.events.HealthChangeEvent;
import mcheese.events.PostMotionUpdateEvent;
import mcheese.events.MotionUpdateEvent;
import mcheese.events.UpdateEvent;
import mcheese.events.VelocityEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Session;
import net.minecraft.world.World;

public class EntityClientPlayerMPHook extends EntityClientPlayerMP
{
	public EntityClientPlayerMPHook(Minecraft p_i45064_1_, World p_i45064_2_, Session p_i45064_3_, NetHandlerPlayClient p_i45064_4_, StatFileWriter p_i45064_5_) 
	{
		super(p_i45064_1_, p_i45064_2_, p_i45064_3_, p_i45064_4_, p_i45064_5_);
		System.out.println("INITIALIZED: " + this.getClass().getSimpleName());
	}
	@Override
	public void onUpdate()
	{
    	//TODO: MCheese
    	UpdateEvent event = new UpdateEvent();
    	MCheese.theClient.getEventManager().callEvent(event);

    	super.onUpdate();
	}
	@Override
	public void setHealth(float health)
	{
		//TODO: MCheese
		HealthChangeEvent event = new HealthChangeEvent(health);
		MCheese.theClient.getEventManager().callEvent(event);
		
		if(event.isCancelled())
		{
			return;
		}
		super.setHealth(health);
	}
	@Override
	public void sendChatMessage(String message)
	{
		//TODO: MCheese
		ChatSendEvent event = new ChatSendEvent(message);
		MCheese.theClient.getEventManager().callEvent(event);
		
		if(event.isCancelled())
		{
			return;
		}		
		super.sendChatMessage(message);
		
	}
	@Override
	public void sendMotionUpdates()
	{
		//TODO: MCheese
		MotionUpdateEvent event = new MotionUpdateEvent();
		MCheese.theClient.getEventManager().callEvent(event);
		
		super.sendMotionUpdates();
		
		//TODO: MCheese
		PostMotionUpdateEvent event2 = new PostMotionUpdateEvent();
		MCheese.theClient.getEventManager().callEvent(event2);
	}
	@Override
	public void addVelocity(double velocityX, double velocityY, double velocityZ)
	{		
		//TODO: MCheese
		VelocityEvent event = new VelocityEvent();
		MCheese.theClient.getEventManager().callEvent(event);
		
		if(event.isCancelled())
		{
			return;
		}
		
		super.addVelocity(velocityX, velocityY, velocityZ);
	}
	@Override
	public void setVelocity(double velocityX, double velocityY, double velocityZ)
	{		
		//TODO: MCheese
		VelocityEvent event = new VelocityEvent();
		MCheese.theClient.getEventManager().callEvent(event);
		
		if(event.isCancelled())
		{
			return;
		}
		
		super.setVelocity(velocityX, velocityY, velocityZ);
	}
}
