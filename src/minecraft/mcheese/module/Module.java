package mcheese.module;

import org.lwjgl.util.Color;

import mcheese.MCheese;
import mcheese.annotations.EventTarget;
import mcheese.event.Cancellable;
import mcheese.event.EventListener;
import mcheese.events.KeyPressEvent;
import mcheese.events.ModuleToggleEvent;
import mcheese.events.PostModuleToggleEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.stream.BroadcastController;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public abstract class Module
{
	private String name;
	private int keybind;
	private ModuleCategory category;
	
	private boolean toggleable = true;
	private boolean toggled = false;
	
	public Module(String name, int keybind, ModuleCategory category)
	{
		this.name = name;
		this.keybind = keybind;
		this.category = category;		
	}
	public String getName()
	{
		return this.name;
	}
	public int getKeybind()
	{
		return this.keybind;
	}
	public ModuleCategory getCategory()
	{
		return this.category;
	}
	public boolean isToggleable()
	{
		return this.toggleable;
	}
	public boolean isToggled()
	{
		return this.toggled;
	}
	
	public void setKeybind(int keybind)
	{
		this.keybind = keybind;
	}
	public void setToggleable(boolean toggleable)
	{
		this.toggleable = toggleable;
	}
	private void setToggled(boolean status)
	{
		this.onToggle();
		if(status)
		{
			MCheese.theClient.getEventManager().registerHandlers((EventListener)this);
			this.onEnable();
		}
		else
		{
			MCheese.theClient.getEventManager().unregisterHandlers((EventListener)this);
			this.onDisable();
		}
		this.toggled = status;
	}
	public void toggleModule()
	{
		if(!this.isToggleable())
		{
			return;
		}
		ModuleToggleEvent event = new ModuleToggleEvent(this);
		MCheese.theClient.getEventManager().callEvent(event);
		if((event instanceof Cancellable) && ((Cancellable)event).isCancelled())
		{
			return;
		}
		//Perform Event
		this.setToggled(!this.isToggled());
		
		PostModuleToggleEvent event2 = new PostModuleToggleEvent(this);
		MCheese.theClient.getEventManager().callEvent(event2);
	}
	public abstract void onToggle();
	public abstract void onEnable();
	public abstract void onDisable();	
}
