package net.minecraft.server.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import io.netty.util.concurrent.GenericFutureListener;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginServer implements INetHandlerLoginServer
{
    private static final AtomicInteger field_147331_b = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final Random field_147329_d = new Random();
    private final byte[] field_147330_e = new byte[4];
    private final MinecraftServer minecraftserver;
    public final NetworkManager netmanager;
    private NetHandlerLoginServer.LoginState login_state;
    private int current_tick;
    private GameProfile gameprofile;
    private String field_147334_j;
    private SecretKey secretkey;
    private static final String __OBFID = "CL_00001458";

    public NetHandlerLoginServer(MinecraftServer minecraftserver, NetworkManager netmanager)
    {
        this.login_state = NetHandlerLoginServer.LoginState.HELLO;
        this.field_147334_j = "";
        this.minecraftserver = minecraftserver;
        this.netmanager = netmanager;
        field_147329_d.nextBytes(this.field_147330_e);
    }

    /**
     * For scheduled network tasks. Used in NetHandlerPlayServer to send keep-alive packets and in NetHandlerLoginServer
     * for a login-timeout
     */
    //TODO: proceeds the login-process to final acceptance (dependent on results from method: processEncryptionResponse)
    public void onNetworkTick()
    {
        if (this.login_state == NetHandlerLoginServer.LoginState.READY_TO_ACCEPT)
        {
            this.attemptFinalizeGameProfile();
        }

        if (this.current_tick++ == 600)
        {
            this.disconnectClient("Took too long to log in");
        }
    }

    public void disconnectClient(String p_147322_1_)
    {
        try
        {
            logger.info("Disconnecting " + this.func_147317_d() + ": " + p_147322_1_);
            ChatComponentText var2 = new ChatComponentText(p_147322_1_);
            this.netmanager.scheduleOutboundPacket(new S00PacketDisconnect(var2), new GenericFutureListener[0]);
            this.netmanager.closeChannel(var2);
        }
        catch (Exception var3)
        {
            logger.error("Error whilst disconnecting player", var3);
        }
    }
    //TODO: checks to see if this gameprofile is complete. If so, it shifts the login-state to ACCEPTED and notifies the client via S02PacketLoginSuccess. Lastly, it allocates the final game-profile in the stored connection.
    public void attemptFinalizeGameProfile()
    {
        if (!this.gameprofile.isComplete())
        {
            this.gameprofile = this.func_152506_a(this.gameprofile);
        }

        String var1 = this.minecraftserver.getConfigurationManager().func_148542_a(this.netmanager.getSocketAddress(), this.gameprofile);

        if (var1 != null)
        {
            this.disconnectClient(var1);
        }
        else
        {
            this.login_state = NetHandlerLoginServer.LoginState.ACCEPTED;
            this.netmanager.scheduleOutboundPacket(new S02PacketLoginSuccess(this.gameprofile), new GenericFutureListener[0]);
            this.minecraftserver.getConfigurationManager().initializeConnectionToPlayer(this.netmanager, this.minecraftserver.getConfigurationManager().func_148545_a(this.gameprofile));
        }
    }

    /**
     * Invoked when disconnecting, the parameter is a ChatComponent describing the reason for termination
     */
    public void onDisconnect(IChatComponent p_147231_1_)
    {
        logger.info(this.func_147317_d() + " lost connection: " + p_147231_1_.getUnformattedText());
    }

    public String func_147317_d()
    {
        return this.gameprofile != null ? this.gameprofile.toString() + " (" + this.netmanager.getSocketAddress().toString() + ")" : String.valueOf(this.netmanager.getSocketAddress());
    }

    /**
     * Allows validation of the connection state transition. Parameters: from, to (connection state). Typically throws
     * IllegalStateException or UnsupportedOperationException if validation fails
     */
    public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_)
    {
        Validate.validState(this.login_state == NetHandlerLoginServer.LoginState.ACCEPTED || this.login_state == NetHandlerLoginServer.LoginState.HELLO, "Unexpected change in protocol", new Object[0]);
        Validate.validState(p_147232_2_ == EnumConnectionState.PLAY || p_147232_2_ == EnumConnectionState.LOGIN, "Unexpected protocol " + p_147232_2_, new Object[0]);
    }
    //TODO: sends Encryption-Request to Client.
    public void processLoginStart(C00PacketLoginStart loginstart)
    {
        Validate.validState(this.login_state == NetHandlerLoginServer.LoginState.HELLO, "Unexpected hello packet", new Object[0]);
        this.gameprofile = loginstart.getGameProfile();

        if (this.minecraftserver.isServerInOnlineMode() && !this.netmanager.isLocalChannel())
        {
            this.login_state = NetHandlerLoginServer.LoginState.KEY;
            this.netmanager.scheduleOutboundPacket(new S01PacketEncryptionRequest(this.field_147334_j, this.minecraftserver.getKeyPair().getPublic(), this.field_147330_e), new GenericFutureListener[0]);
        }
        else
        {
            this.login_state = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
        }
    }
    //TODO: EXPLOIT
    public void processEncryptionResponse(C01PacketEncryptionResponse response)
    {
        Validate.validState(this.login_state == NetHandlerLoginServer.LoginState.KEY, "Unexpected key packet", new Object[0]);
        PrivateKey var2 = this.minecraftserver.getKeyPair().getPrivate();

        if (!Arrays.equals(this.field_147330_e, response.func_149299_b(var2)))
        {
            throw new IllegalStateException("Invalid nonce!");
        }
        else
        {
            this.secretkey = response.func_149300_a(var2);
            this.login_state = NetHandlerLoginServer.LoginState.AUTHENTICATING;
            this.netmanager.enableEncryption(this.secretkey);
            (new Thread("User Authenticator #" + field_147331_b.incrementAndGet())
            {
                private static final String __OBFID = "CL_00001459";
                public void run()
                {
                    GameProfile gp = NetHandlerLoginServer.this.gameprofile;

                    try
                    {
                        String var2 = (new BigInteger(CryptManager.getServerIdHash(NetHandlerLoginServer.this.field_147334_j, NetHandlerLoginServer.this.minecraftserver.getKeyPair().getPublic(), NetHandlerLoginServer.this.secretkey))).toString(16);
                        NetHandlerLoginServer.this.gameprofile = NetHandlerLoginServer.this.minecraftserver.func_147130_as().hasJoinedServer(new GameProfile((UUID)null, gp.getName()), var2);

                        if (NetHandlerLoginServer.this.gameprofile != null)
                        {
                            NetHandlerLoginServer.logger.info("UUID of player " + NetHandlerLoginServer.this.gameprofile.getName() + " is " + NetHandlerLoginServer.this.gameprofile.getId());
                            NetHandlerLoginServer.this.login_state = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
                        }
                        else if (NetHandlerLoginServer.this.minecraftserver.isSinglePlayer())
                        {
                            NetHandlerLoginServer.logger.warn("Failed to verify username but will let them in anyway!");
                            NetHandlerLoginServer.this.gameprofile = NetHandlerLoginServer.this.func_152506_a(gp);
                            NetHandlerLoginServer.this.login_state = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
                        }
                        else
                        {
                            NetHandlerLoginServer.this.disconnectClient("Failed to verify username!");
                            NetHandlerLoginServer.logger.error("Username \'" + NetHandlerLoginServer.this.gameprofile.getName() + "\' tried to join with an invalid session");
                        }
                    }
                    catch (AuthenticationUnavailableException var3)
                    {
                        if (NetHandlerLoginServer.this.minecraftserver.isSinglePlayer())
                        {
                            NetHandlerLoginServer.logger.warn("Authentication servers are down but will let them in anyway!");
                            NetHandlerLoginServer.this.gameprofile = NetHandlerLoginServer.this.func_152506_a(gp);
                            NetHandlerLoginServer.this.login_state = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
                        }
                        else
                        {
                            NetHandlerLoginServer.this.disconnectClient("Authentication servers are down. Please try again later, sorry!");
                            NetHandlerLoginServer.logger.error("Couldn\'t verify username because servers are unavailable");
                        }
                    }
                }
            }).start();
        }
    }

    protected GameProfile func_152506_a(GameProfile p_152506_1_)
    {
        UUID var2 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + p_152506_1_.getName()).getBytes(Charsets.UTF_8));
        return new GameProfile(var2, p_152506_1_.getName());
    }

    static enum LoginState
    {
        HELLO("HELLO", 0),
        KEY("KEY", 1),
        AUTHENTICATING("AUTHENTICATING", 2),
        READY_TO_ACCEPT("READY_TO_ACCEPT", 3),
        ACCEPTED("ACCEPTED", 4);

        private static final NetHandlerLoginServer.LoginState[] $VALUES = new NetHandlerLoginServer.LoginState[]{HELLO, KEY, AUTHENTICATING, READY_TO_ACCEPT, ACCEPTED};
        private static final String __OBFID = "CL_00001463";

        private LoginState(String p_i45297_1_, int p_i45297_2_) {}
    }
}
