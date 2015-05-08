package mcheese.cmd;

import java.util.ArrayList;
import java.util.Arrays;

import mcheese.MCheese;
import mcheese.annotations.EventTarget;
import mcheese.cmd.commands.CommandBind;
import mcheese.cmd.commands.CommandHelp;
import mcheese.cmd.commands.CommandList;
import mcheese.cmd.commands.CommandToggle;
import mcheese.event.EventListener;
import mcheese.events.ChatReceiveEvent;
import mcheese.events.ChatSendEvent;

public class CommandManager implements EventListener
{
	private ArrayList<Command> commands = new ArrayList<Command>();
	
	public CommandManager()
	{
		this.registerCommands();
		MCheese.theClient.getEventManager().registerHandlers((EventListener)this);
	}
	private void registerCommands()
	{
		this.commands.clear();
		
		//TODO: Commands are registered here
		this.commands.add(new CommandHelp());
		
		this.commands.add(new CommandBind());
		this.commands.add(new CommandList());
		this.commands.add(new CommandToggle());
		//this.commands.add(new CommandSpoof());
	}
	public boolean handleCommand(String prefix, String args[])
	{
		if(!prefix.equalsIgnoreCase(MCheese.COMMAND_PREFIX))
		{
			return false;
		}
		if(args.length == 0)
		{
			for(Command cmd : this.commands)
			{
				if(cmd.getType() == CommandType.HELP)
				{
					cmd.executeCommand(new String[0]);
				}
			}
			return true;
		}
		String cmdlabel = args[0];
		String cmdargs[] = Arrays.copyOfRange(args, 1, args.length);
		
		for(Command cmd : this.commands)
		{
			if(cmd.getLabel().equalsIgnoreCase(cmdlabel))
			{
				boolean result = cmd.executeCommand(cmdargs);
				if(!result)
				{
					MCheese.theClient.addChatMessage(MCheese.theClient.getMessageManager().getInvalidSyntaxMessage(cmd));
				}
				break;
			}
		}
		return true;
	}
	public ArrayList<Command> getCommands()
	{
		return this.commands;
	}
	@EventTarget
	public void onChatSend(ChatSendEvent event)
	{
		String words[] = event.getMessage().trim().split(" ");
		String prefix = words[0];
		String args[] = Arrays.copyOfRange(words, 1, words.length);
				
		if(this.handleCommand(prefix, args))
		{
			event.setCancelled(true);
		}
	}
}
