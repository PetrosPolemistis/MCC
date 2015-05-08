package mcheese.msg;

import net.minecraft.util.EnumChatFormatting;
import mcheese.MCheese;
import mcheese.cmd.Command;
import mcheese.module.Module;

public class MessageManager 
{
	public String getOnModuleEnableMessage(Module module)
	{
		return module.getName() + " enabled.";
	}
	public String getOnModuleDisableMessage(Module module)
	{
		return module.getName() + " disabled.";
	}
	public String getInvalidSyntaxMessage(Command cmd)
	{
		return EnumChatFormatting.RED + "Invalid Syntax, " + MCheese.COMMAND_PREFIX + " " + cmd.getLabel() + (cmd.getSyntax() == null ? "" : " " + cmd.getSyntax());
	}
}
