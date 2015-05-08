package mcheese.hooks;

import mcheese.MCheese;
import mcheese.events.ChatReceiveEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class GuiNewChatHook extends GuiNewChat
{
	
	public GuiNewChatHook(Minecraft mc) 
	{
		super(mc);
		System.out.println("INITIALIZED: " + this.getClass().getSimpleName());
	}
	@Override
	public void printChatMessage(IChatComponent chatComponent)
	{
		//TODO: MCheese
		ChatReceiveEvent event = new ChatReceiveEvent(chatComponent.getFormattedText());
		MCheese.theClient.getEventManager().callEvent(event);
		
		if(event.isCancelled())
		{
			return;
		}
		chatComponent = new ChatComponentText(event.getMessage());
		super.printChatMessage(chatComponent);
	}
}
