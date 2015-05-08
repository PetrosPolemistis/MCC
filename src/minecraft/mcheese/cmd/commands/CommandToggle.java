package mcheese.cmd.commands;

import mcheese.MCheese;
import mcheese.cmd.Command;
import mcheese.cmd.CommandType;
import mcheese.module.Module;

public class CommandToggle extends Command
{

	public CommandToggle() 
	{
		super("toggle", CommandType.DEFAULT, "<module>");
	}

	@Override
	public boolean executeCommand(String[] args) 
	{
		if(args.length < 1 || args.length > 1)
		{
			return false;
		}
		Module module = MCheese.theClient.getModuleManager().getModuleByName(args[0]);
		
		if(module == null)
		{
			return false;
		}
		
		module.toggleModule();
		
		return true;
	}

}
