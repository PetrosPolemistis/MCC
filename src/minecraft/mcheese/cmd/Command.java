package mcheese.cmd;

public abstract class Command 
{
	private CommandType type;
	
	private String label;
	private String syntax;
	
	public Command(String label, CommandType type, String syntax)
	{
		this.type = type;
		this.label = label.toLowerCase();
		this.syntax = syntax;
	}
	
	public abstract boolean executeCommand(String args[]);
	
	public CommandType getType()
	{
		return this.type;
	}
	public String getLabel()
	{
		return this.label;
	}
	public String getSyntax()
	{
		return this.syntax;
	}
}
