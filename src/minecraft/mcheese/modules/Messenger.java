package mcheese.modules;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import mcheese.MCheese;
import mcheese.annotations.EventTarget;
import mcheese.event.EventListener;
import mcheese.events.PostModuleToggleEvent;
import mcheese.module.Module;
import mcheese.module.ModuleCategory;

public class Messenger extends Module implements EventListener
{	
	private List<ModuleCategory> enabledCategories = Arrays.asList(ModuleCategory.values());
	private boolean isOnModuleEnable = true;
	private boolean isOnModuleDisable = true;
		
	public Messenger() 
	{
		super("Messages", Keyboard.KEY_Z, ModuleCategory.OTHER);
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
	public List<ModuleCategory> getEnabledCategories()
	{
		return this.enabledCategories;
	}
	public boolean getOnModuleEnable()
	{
		return this.isOnModuleEnable;
	}
	public boolean getOnModuleDisable()
	{
		return this.isOnModuleDisable;
	}
	public void setEnabledCategories(List<ModuleCategory> enabledCategories)
	{
		this.enabledCategories = enabledCategories;
	}
	public void removeEnabledCategory(ModuleCategory category)
	{
		if(this.enabledCategories.contains(category))
		{
			this.enabledCategories.remove(category);
		}
	}
	public void addEnabledCategory(ModuleCategory category)
	{
		if(!this.enabledCategories.contains(category))
		{
			this.enabledCategories.add(category);
		}
	}
	public void setOnModuleEnable(boolean isOnModuleEnable)
	{
		this.isOnModuleEnable = isOnModuleEnable;
	}
	public void setOnModuleDisable(boolean isOnModuleDisable)
	{
		this.isOnModuleDisable = isOnModuleDisable;
	}
	@EventTarget
	public void onPostModuleToggle(PostModuleToggleEvent event)
	{
		if(!this.isToggled())
		{
			return;
		}
		Module e_module = event.getModule();
		
		if(e_module instanceof Messenger)
		{
			return;
		}
		if(!this.enabledCategories.contains(e_module.getCategory()))
		{
			return;
		}
		if(e_module.isToggled())
		{
			if(this.isOnModuleEnable)
			{
				this.onModuleEnable(e_module);
			}
		}
		else
		{
			if(this.isOnModuleDisable)
			{
				this.onModuleDisable(e_module);
			}
		}
	}
	private void onModuleEnable(Module module)
	{
		MCheese.theClient.addChatMessage(MCheese.theClient.getMessageManager().getOnModuleEnableMessage(module));
	}
	private void onModuleDisable(Module module)
	{
		MCheese.theClient.addChatMessage(MCheese.theClient.getMessageManager().getOnModuleDisableMessage(module));
	}
}
