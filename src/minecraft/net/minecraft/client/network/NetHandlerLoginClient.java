package net.minecraft.client.network;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.traffic.GlobalTrafficShapingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.security.PublicKey;
import java.util.UUID;
import javax.crypto.SecretKey;

import mcheese.hooks.NetHandlerPlayClientHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginClient implements INetHandlerLoginClient
{
    private static final Logger logger = LogManager.getLogger();
    private final Minecraft mc_inst;
    private final GuiScreen guiscreen;
    private final NetworkManager netmanager;
    private static final String __OBFID = "CL_00000876";

    public NetHandlerLoginClient(NetworkManager p_i45059_1_, Minecraft p_i45059_2_, GuiScreen p_i45059_3_)
    {
        this.netmanager = p_i45059_1_;
        this.mc_inst = p_i45059_2_;
        this.guiscreen = p_i45059_3_;
    }
    //TODO: server encryption-request handling.
    public void handleEncryptionRequest(S01PacketEncryptionRequest encryptionrequest)
    {    	
        final SecretKey var2 = CryptManager.createNewSharedKey();
        String var3 = encryptionrequest.func_149609_c();
        PublicKey var4 = encryptionrequest.func_149608_d();
        String var5 = (new BigInteger(CryptManager.getServerIdHash(var3, var4, var2))).toString(16);
        boolean var6 = this.mc_inst.func_147104_D() == null || !this.mc_inst.func_147104_D().func_152585_d();

        try
        {
            this.getNewMinecraftSessionService().joinServer(this.mc_inst.getSession().func_148256_e(), this.mc_inst.getSession().getToken(), var5);
        }
        catch (AuthenticationUnavailableException var8)
        {
            if (var6)
            {
                this.netmanager.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] {new ChatComponentTranslation("disconnect.loginFailedInfo.serversUnavailable", new Object[0])}));
                return;
            }
        }
        catch (InvalidCredentialsException var9)
        {
            if (var6)
            {
                this.netmanager.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] {new ChatComponentTranslation("disconnect.loginFailedInfo.invalidSession", new Object[0])}));
                return;
            }
        }
        catch (AuthenticationException var10)
        {
            if (var6)
            {
                this.netmanager.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] {var10.getMessage()}));
                return;
            }
        }
        NetHandlerLoginClient.this.netmanager.scheduleOutboundPacket(new C01PacketEncryptionResponse(var2, var4, encryptionrequest.func_149607_e()), new GenericFutureListener[] {new GenericFutureListener()
        {
            private static final String __OBFID = "CL_00000877";
            public void operationComplete(Future p_operationComplete_1_)
            {
                NetHandlerLoginClient.this.netmanager.enableEncryption(var2);
            }
        } });

    }
    //TODO: Fetch Yggdrasil-Authenticator.
    private MinecraftSessionService getNewMinecraftSessionService()
    {
        return (new YggdrasilAuthenticationService(this.mc_inst.getProxy(), UUID.randomUUID().toString())).createMinecraftSessionService();
    }

    public void handleLoginSuccess(S02PacketLoginSuccess p_147390_1_)
    {
        this.netmanager.setConnectionState(EnumConnectionState.PLAY);
    }

    /**
     * Invoked when disconnecting, the parameter is a ChatComponent describing the reason for termination
     */
    public void onDisconnect(IChatComponent p_147231_1_)
    {
        this.mc_inst.displayGuiScreen(new GuiDisconnected(this.guiscreen, "connect.failed", p_147231_1_));
    }

    /**
     * Allows validation of the connection state transition. Parameters: from, to (connection state). Typically throws
     * IllegalStateException or UnsupportedOperationException if validation fails
     */
    //TODO: MCheese
    public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_)
    {
        logger.debug("Switching protocol from " + p_147232_1_ + " to " + p_147232_2_);

        if (p_147232_2_ == EnumConnectionState.PLAY)
        {
            this.netmanager.setNetHandler(new NetHandlerPlayClient(this.mc_inst, this.guiscreen, this.netmanager));
        }
    }

    /**
     * For scheduled network tasks. Used in NetHandlerPlayServer to send keep-alive packets and in NetHandlerLoginServer
     * for a login-timeout
     */
    public void onNetworkTick() {}

    public void handleDisconnect(S00PacketDisconnect p_147388_1_)
    {
        this.netmanager.closeChannel(p_147388_1_.func_149603_c());
    }
}
