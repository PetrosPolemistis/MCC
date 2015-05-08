package mcheese.ui;

import mcheese.MCheese;
import mcheese.font.Fonts;
import net.minecraft.client.gui.Gui;

public class UIRenderer 
{
	public static void renderUI()
	{
		Gui.drawString(Fonts.tahoma24, MCheese.theClient.getName() + " v" + MCheese.theClient.getVersion(), 2, 2, 0x99EAC530);
		renderAndUpdateFrames();
	}
	public static void renderAndUpdateFrames()
	{
		/*MCheese.theClient.getGuiManager().update();
		if(MCheese.MC.currentScreen instanceof GuiManager)
			MCheese.theClient.getGuiManager().render();
		else
			MCheese.theClient.getGuiManager().renderPinned();
			*/
	}
}
