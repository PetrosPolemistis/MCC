package mcheese.cmd.commands;

import net.minecraft.util.EnumChatFormatting;
import mcheese.MCheese;
import mcheese.cmd.Command;
import mcheese.cmd.CommandType;

public class CommandHelp extends Command
{

	public CommandHelp() 
	{
		super("help", CommandType.HELP, null);
	}

	@Override
	public boolean executeCommand(String[] args)
	{
		if(args.length == 0)
		{
			EnumChatFormatting color = EnumChatFormatting.GREEN;
			final String CMD_PREFIX = MCheese.COMMAND_PREFIX;
			
			for(Command cmd : MCheese.theClient.getCommandManager().getCommands())
			{
				if(cmd.getType() == CommandType.HELP)
				{
					continue;
				}
				MCheese.theClient.addChatMessage(color + CMD_PREFIX + " " + cmd.getLabel() + (cmd.getSyntax() == null ? "" : " " + cmd.getSyntax()));
			}
			return true;
		}
		return false;
	}
	
}
