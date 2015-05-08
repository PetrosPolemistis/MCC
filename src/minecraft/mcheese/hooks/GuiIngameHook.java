package mcheese.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;

public class GuiIngameHook extends GuiIngame
{	
	private Minecraft mc;

	public GuiIngameHook(Minecraft mc) 
	{
		super(mc);
		System.out.println("INITIALIZED: " + this.getClass().getSimpleName());
		
		this.mc = mc;
		this.persistantChatGUI = new GuiNewChatHook(this.mc);
	}
	
}
