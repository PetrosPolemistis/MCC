package mcheese.modules.combat;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import mcheese.MCheese;
import mcheese.annotations.EventTarget;
import mcheese.event.EventListener;
import mcheese.events.UpdateEvent;
import mcheese.module.ModuleCategory;
import mcheese.module.Module;

public class KillAura extends Module implements EventListener
{

	private long currentTime = 0L;
	private long lastTime = 0L;
	
	private float hitRange = 4.6F; //TODO
	private long hitDelay = 200L;
	private KillAuraMode mode = KillAuraMode.BOTH;
	
	public static enum KillAuraMode
	{
		MOB,
		PLAYER,
		BOTH;
	}
	
	public KillAura() 
	{
		super("KillAura", Keyboard.KEY_F, ModuleCategory.COMBAT);
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
	public float getHitRange()
	{
		return this.hitRange;
	}
	public long getHitDelay()
	{
		return this.hitDelay;
	}
	public void setHitRange(float hitRange)
	{
		this.hitRange = hitRange;
	}
	public void setHitDelay(long hitDelay)
	{
		this.hitDelay = hitDelay;
	}
	public KillAuraMode getMode()
	{
		return this.mode;
	}
	public void setMode(KillAuraMode mode)
	{
		this.mode = mode;
	}
	
	@EventTarget
	public void onPlayerUpdate(UpdateEvent event)
	{
		if(!this.isToggled())
		{
			return;
		}
		this.currentTime = System.nanoTime() / 1000000;
		if(!this.hasDelayed(this.hitDelay))
		{
			return;
		}
		loopEntities: 
		for(Object eo : MCheese.MC.theWorld.loadedEntityList)
		{
			if(!(eo instanceof EntityLivingBase))
			{
				continue loopEntities;
			}
			
			EntityLivingBase target = (EntityLivingBase) eo;
			EntityPlayer pl = (eo instanceof EntityPlayer) ? (EntityPlayer)eo : null;

			if(MCheese.MC.thePlayer.getDistanceToEntity(target) > this.hitRange)
			{
				continue loopEntities;
			}
			if((this.mode == KillAuraMode.MOB && (eo instanceof EntityLivingBase) && !(eo instanceof EntityPlayer))
					|| (this.mode == KillAuraMode.PLAYER && (eo instanceof EntityPlayer))
					|| (this.mode == KillAuraMode.BOTH && (eo instanceof EntityLivingBase)))
			{				
				if(MCheese.MC.thePlayer.equals(target))
				{
					continue loopEntities;
				}
				if(pl != null)
				{
					//TODO add Friend support etc..
				}
				MCheese.MC.thePlayer.swingItem();
				MCheese.MC.playerController.attackEntity(MCheese.MC.thePlayer, target);
				
				this.lastTime = System.nanoTime() / 1000000;
				//TODO: optional: break; /*Attack a single entity and then delay again*/
			}
		}
	}
	//TODO: debug, implement with KillAura.
	public void faceEntity(Entity theEntity)
	{
	    double x = theEntity.posX - MCheese.MC.thePlayer.posX;
	    double z = theEntity.posZ - MCheese.MC.thePlayer.posZ;
	    double y = theEntity.posY + theEntity.getEyeHeight() / 1.4D - MCheese.MC.thePlayer.posY + MCheese.MC.thePlayer.getEyeHeight() / 1.4D;
	    double helper = MathHelper.sqrt_double(x * x + z * z);

	    float newYaw = (float)Math.toDegrees(-Math.atan(x / z));
	    float newPitch = (float)-Math.toDegrees(Math.atan(y / helper));

	    if ((z < 0.0D) && (x < 0.0D)) newYaw = (float)(90.0D + Math.toDegrees(Math.atan(z / x)));
	    else if ((z < 0.0D) && (x > 0.0D)) newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(z / x)));

	    MCheese.MC.thePlayer.rotationYaw = newYaw;
	    MCheese.MC.thePlayer.rotationPitch = (newPitch + 100.0F);
	    MCheese.MC.thePlayer.rotationYawHead = newPitch;
	}
	private boolean hasDelayed(long milliseconds)
	{
		return (this.currentTime - this.lastTime >= milliseconds);
	}
}
