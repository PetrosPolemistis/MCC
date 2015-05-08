package mcheese.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S01PacketJoinGame;

public class NetHandlerPlayClientHook extends NetHandlerPlayClient
{

	public NetHandlerPlayClientHook(Minecraft mc, GuiScreen gui, NetworkManager netManager) 
	{
		super(mc, gui, netManager);
		System.out.println("INITIALIZED: " + this.getClass().getSimpleName());

	    Minecraft.getMinecraft().playerController = new PlayerControllerMPHook(Minecraft.getMinecraft(), this);
	}
	@Override
	public void handleJoinGame(S01PacketJoinGame packet)
	{
		super.handleJoinGame(packet);
	    Minecraft.getMinecraft().playerController = new PlayerControllerMPHook(Minecraft.getMinecraft(), this);
	}
	@Override
	public NetworkManager getNetworkManager()
	{
		return Minecraft.getMinecraft().myNetworkManager;
	}
}
