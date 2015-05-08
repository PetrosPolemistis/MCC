package mcheese.cmd.commands;

import net.minecraft.util.EnumChatFormatting;
import mcheese.MCheese;
import mcheese.cmd.Command;
import mcheese.cmd.CommandType;
import mcheese.module.Module;
import mcheese.module.ModuleCategory;

public class CommandList extends Command
{

	public CommandList() 
	{
		super("List", CommandType.DEFAULT, null);
	}

	@Override
	public boolean executeCommand(String[] args) 
	{
		String text = "";
		
		for(Module module : MCheese.theClient.getModuleManager().getModules())
		{
			EnumChatFormatting color = module.isToggled() ? EnumChatFormatting.GREEN : EnumChatFormatting.GRAY;
			
			if(module.getCategory() != ModuleCategory.OTHER)
			{
				text += (color + module.getName() + (EnumChatFormatting.YELLOW + ", ")); 
			}
		}
		MCheese.theClient.addChatMessage(text);
		
		return true;
	}

}
