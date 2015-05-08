package mcheese.hooks;

import mcheese.MCheese;
import mcheese.events.RenderEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;

public class EntityRendererHook extends EntityRenderer
{

	public EntityRendererHook(Minecraft p_i45076_1_, IResourceManager p_i45076_2_) 
	{
		super(p_i45076_1_, p_i45076_2_);
		System.out.println("INITIALIZED: " + this.getClass().getSimpleName());
	}
	@Override
	public void renderWorld(float par1, long lon1)
	{
		//TODO: MCheese
		RenderEvent event = new RenderEvent();
		MCheese.theClient.getEventManager().callEvent(event);
		
		super.renderWorld(par1, lon1);
	}

}
