package net.minecraft.client.multiplayer;

import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

import mcheese.MCheese;
import mcheese.hooks.NetworkManagerHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiConnecting extends GuiScreen
{
    private static final AtomicInteger field_146372_a = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private NetworkManager netManager;
    private boolean field_146373_h;
    private final GuiScreen guiscreen;
    private static final String __OBFID = "CL_00000685";

    public GuiConnecting(GuiScreen p_i1181_1_, Minecraft mc, ServerData p_i1181_3_)
    {
        this.mc = mc;
        this.guiscreen = p_i1181_1_;
        ServerAddress var4 = ServerAddress.func_78860_a(p_i1181_3_.serverIP);
        mc.loadWorld((WorldClient)null);
        mc.setServerData(p_i1181_3_);
        this.startHandshake(var4.getIP(), var4.getPort());
    }

    public GuiConnecting(GuiScreen p_i1182_1_, Minecraft p_i1182_2_, String p_i1182_3_, int p_i1182_4_)
    {
        this.mc = p_i1182_2_;
        this.guiscreen = p_i1182_1_;
        p_i1182_2_.loadWorld((WorldClient)null);
        this.startHandshake(p_i1182_3_, p_i1182_4_);
    }
    //TODO: MCheese -> EXPLOIT. change 'public' to 'private' when done.
    public void startHandshake(final String serveraddress, final int serverport)
    {
        logger.info("Connecting to " + serveraddress + ", " + serverport);
        (new Thread("Server Connector #" + field_146372_a.incrementAndGet())
        {
            private static final String __OBFID = "CL_00000686";
            public void run()
            {
                InetAddress var1 = null;

                try
                {
                    if (GuiConnecting.this.field_146373_h)
                    {
                        return;
                    }

                    var1 = InetAddress.getByName(serveraddress);
                    GuiConnecting.this.netManager = NetworkManagerHook.provideLanClient(var1, serverport);
                    GuiConnecting.this.netManager.setNetHandler(new NetHandlerLoginClient(GuiConnecting.this.netManager, GuiConnecting.this.mc, GuiConnecting.this.guiscreen));
                    GuiConnecting.this.netManager.scheduleOutboundPacket(new C00Handshake(5, serveraddress, serverport, EnumConnectionState.LOGIN), new GenericFutureListener[0]);
                    GuiConnecting.this.netManager.scheduleOutboundPacket(new C00PacketLoginStart(GuiConnecting.this.mc.getSession().func_148256_e()), new GenericFutureListener[0]);
                }
                catch (UnknownHostException var5)
                {
                    if (GuiConnecting.this.field_146373_h)
                    {
                        return;
                    }

                    GuiConnecting.logger.error("Couldn\'t connect to server", var5);
                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.guiscreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {"Unknown host"})));
                }
                catch (Exception var6)
                {
                    if (GuiConnecting.this.field_146373_h)
                    {
                        return;
                    }

                    GuiConnecting.logger.error("Couldn\'t connect to server", var6);
                    String var3 = var6.toString();

                    if (var1 != null)
                    {
                        String var4 = var1.toString() + ":" + serverport;
                        var3 = var3.replaceAll(var4, "");
                    }

                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.guiscreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {var3})));
                }
            }
        }).start();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (this.netManager != null)
        {
            if (this.netManager.isChannelOpen())
            {
                this.netManager.processReceivedPackets();
            }
            else if (this.netManager.getExitMessage() != null)
            {
                this.netManager.getNetHandler().onDisconnect(this.netManager.getExitMessage());
            }
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {}

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + 50, I18n.format("gui.cancel", new Object[0])));
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        if (p_146284_1_.id == 0)
        {
            this.field_146373_h = true;

            if (this.netManager != null)
            {
                this.netManager.closeChannel(new ChatComponentText("Aborted"));
            }

            this.mc.displayGuiScreen(this.guiscreen);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        this.drawDefaultBackground();

        if (this.netManager == null)
        {
            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
        }
        else
        {
            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
        }

        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}
