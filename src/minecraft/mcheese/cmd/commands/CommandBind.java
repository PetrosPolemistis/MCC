package mcheese.cmd.commands;

import org.lwjgl.input.Keyboard;

import mcheese.MCheese;
import mcheese.cmd.Command;
import mcheese.cmd.CommandType;
import mcheese.module.Module;

public class CommandBind extends Command
{

	public CommandBind()
	{
		super("bind", CommandType.DEFAULT, "[add|remove|get] <module> <key>");
	}
	
	@Override
	public boolean executeCommand(String args[])
	{		
		if(args.length < 2 || args.length > 3)
		{
			return false;
		}
		
		Module module = MCheese.theClient.getModuleManager().getModuleByName(args[1]);
		
		if(module == null)
		{
			return false;
		}
		
		if(args[0].equalsIgnoreCase("add"))
		{
			if(args.length < 3)
			{
				return false;
			}
			
			int key = Keyboard.getKeyIndex(args[2].toUpperCase());
			
			if(key == Keyboard.KEY_NONE)
			{
				return false;
			}
			
			module.setKeybind(key);
			MCheese.theClient.addChatMessage("Set keybind of module " + module.getName() + " to " + Keyboard.getKeyName(key));
		}
		else if(args[0].equalsIgnoreCase("remove"))
		{			
			module.setKeybind(Keyboard.KEY_NONE);
			MCheese.theClient.addChatMessage("Removed the keybind of module " + module.getName());
		}
		else if(args[0].equalsIgnoreCase("get"))
		{
			MCheese.theClient.addChatMessage("Keybind of module " + module.getName() + " = " + Keyboard.getKeyName(module.getKeybind()));
		}
		else
		{
			return false;
		}
		
		return true;
	}
	
}
