package mcheese.modules;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;

import org.lwjgl.input.Keyboard;

import mcheese.MCheese;
import mcheese.annotations.EventTarget;
import mcheese.event.EventListener;
import mcheese.events.KeyPressEvent;
import mcheese.module.Module;
import mcheese.module.ModuleCategory;

public class ClickGui extends Module implements EventListener
{	
	public ClickGui() 
	{
		super("ClickGUI", Keyboard.KEY_Y, ModuleCategory.OTHER);
	}

	@Override
	public void onToggle() {}
	
	@Override
	public void onEnable()
	{
		/*if(!(MCheese.MC.currentScreen instanceof GuiManager))
		{
			MCheese.MC.displayGuiScreen(MCheese.theClient.getGuiManager());
		}*/
	}
	@Override
	public void onDisable() {}
	
	@EventTarget
	public void onKeyPress(KeyPressEvent event)
	{
		if(event.getKey() != Keyboard.KEY_ESCAPE)
		{
			return;
		}
		if(this.isToggled())
		{
			this.toggleModule();
		}
	}
}
