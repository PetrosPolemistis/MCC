package mcheese.module;

import java.util.ArrayList;

import mcheese.modules.*;
import mcheese.modules.combat.*;
import mcheese.modules.player.*;
import mcheese.modules.tools.Ping;

public class ModuleManager 
{
	private ArrayList<Module> modules = new ArrayList<Module>();
	
	public ModuleManager()
	{
		this.registerModules();
	}
	private void registerModules()
	{
		this.modules.clear();
		
		//TODO: Modules are registered here
		//this.modules.add(new ClickGui());
		this.modules.add(new Messenger());
		
		this.modules.add(new AntiKnockback());
		this.modules.add(new Blink());
		this.modules.add(new KillAura());
		this.modules.add(new Sprint());
		//this.modules.add(new Regen());
		
		this.modules.add(new Flight());
		this.modules.add(new Sneak());
		this.modules.add(new PacketSneak());
		//this.modules.add(new Jesus());
		
		//this.modules.add(new Ping());
		//this.modules.add(new ChatSpoof());
	}
	public ArrayList<Module> getModules()
	{
		return this.modules;
	}
	public int getModuleID(Module module)
	{
		if(modules.contains(module))
		{
			return this.modules.indexOf(module);
		}
		else {
			return -1;
		}
	}
	public Module getModuleByName(String name)
	{
		for(Module module : this.modules)
		{
			if(module.getName().equalsIgnoreCase(name))
			{
				return module;
			}
		}
		return null;
	}
}	
