package mcheese;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandHandler;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import mcheese.cmd.CommandManager;
import mcheese.event.EventListener;
import mcheese.event.EventManager;
import mcheese.module.Module;
import mcheese.module.ModuleManager;
import mcheese.msg.MessageManager;

public class MCheese
{	
	public static final MCheese theClient = new MCheese("MCC", 1.1);
	public static final Minecraft MC = Minecraft.getMinecraft();
	//TODO: attain command send hook.
	public static final String COMMAND_PREFIX = ".mcc";
	
	private String name;
	private double version;
	
	private MessageManager messageManager;
	private ModuleManager moduleManager;
	private EventManager eventManager;
	private CommandManager commandManager;
	//private GuiManager guiManager;
	
	private MCheese(String name, double version)
	{
		this.name = name;
		this.version = version;
	}
	public void startClient()
	{
		this.init();
	}
	private void init()
	{
		this.messageManager = new MessageManager();
		this.moduleManager = new ModuleManager();
		this.eventManager = new EventManager();
		this.commandManager = new CommandManager();
		//this.guiManager = new GuiManager();
	}
	public String getName()
	{		
		return this.name;
	}
	public double getVersion()
	{
		return this.version;
	}
	public MessageManager getMessageManager()
	{
		return this.messageManager;
	}
	public ModuleManager getModuleManager()
	{
		return this.moduleManager;
	}
	public EventManager getEventManager()
	{
		return this.eventManager;
	}
	public CommandManager getCommandManager()
	{
		return this.commandManager;
	}
	/*public GuiManager getGuiManager()
	{
		return this.guiManager;
	}*/
	public void addChatMessage(String message)
	{
		IChatComponent chatcomp = new ChatComponentText(EnumChatFormatting.GOLD + "["+this.name+"] " + EnumChatFormatting.YELLOW + message);
		MC.thePlayer.addChatMessage(chatcomp);
	}
}
