package mcheese.hooks;

import mcheese.MCheese;
import mcheese.events.AttackEntityEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.world.World;

public class PlayerControllerMPHook extends PlayerControllerMP
{

	public PlayerControllerMPHook(Minecraft mc, NetHandlerPlayClient netHandler) 
	{
		super(mc, netHandler);
		System.out.println("INITIALIZED: " + this.getClass().getSimpleName());
	}
	@Override
	public void attackEntity(EntityPlayer pe, Entity e)
	{
		//TODO: MCheese
		AttackEntityEvent event = new AttackEntityEvent(e);
		MCheese.theClient.getEventManager().callEvent(event);
		
		if(event.isCancelled())
		{
			return;
		}
		
		super.attackEntity(pe, e);
	}
	@Override
	public EntityClientPlayerMPHook func_147493_a(World w, StatFileWriter sfw)
	{
	    return new EntityClientPlayerMPHook(this.mc, w, this.mc.getSession(), this.netClientHandler, sfw);
	}
}
