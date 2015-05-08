package net.minecraft.server.network;

import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class NetHandlerHandshakeTCP implements INetHandlerHandshakeServer
{
    private final MinecraftServer minecraftserver;
    private final NetworkManager netmanager;
    private static final String __OBFID = "CL_00001456";

    public NetHandlerHandshakeTCP(MinecraftServer p_i45295_1_, NetworkManager p_i45295_2_)
    {
        this.minecraftserver = p_i45295_1_;
        this.netmanager = p_i45295_2_;
    }

    /**
     * There are two recognized intentions for initiating a handshake: logging in and acquiring server status. The
     * NetworkManager's protocol will be reconfigured according to the specified intention, although a login-intention
     * must pass a versioncheck or receive a disconnect otherwise
     */
    public void processHandshake(C00Handshake handshakerequest)
    {
        switch (NetHandlerHandshakeTCP.SwitchEnumConnectionState.field_151291_a[handshakerequest.getConnectionState().ordinal()])
        {
            case 1:
                this.netmanager.setConnectionState(EnumConnectionState.LOGIN);
                ChatComponentText var2;

                if (handshakerequest.func_149595_d() > 5)
                {
                    var2 = new ChatComponentText("Outdated server! I\'m still on 1.7.10");
                    this.netmanager.scheduleOutboundPacket(new S00PacketDisconnect(var2), new GenericFutureListener[0]);
                    this.netmanager.closeChannel(var2);
                }
                else if (handshakerequest.func_149595_d() < 5)
                {
                    var2 = new ChatComponentText("Outdated client! Please use 1.7.10");
                    this.netmanager.scheduleOutboundPacket(new S00PacketDisconnect(var2), new GenericFutureListener[0]);
                    this.netmanager.closeChannel(var2);
                }
                else
                {
                    this.netmanager.setNetHandler(new NetHandlerLoginServer(this.minecraftserver, this.netmanager));
                }

                break;

            case 2:
                this.netmanager.setConnectionState(EnumConnectionState.STATUS);
                this.netmanager.setNetHandler(new NetHandlerStatusServer(this.minecraftserver, this.netmanager));
                break;

            default:
                throw new UnsupportedOperationException("Invalid intention " + handshakerequest.getConnectionState());
        }
    }

    /**
     * Invoked when disconnecting, the parameter is a ChatComponent describing the reason for termination
     */
    public void onDisconnect(IChatComponent p_147231_1_) {}

    /**
     * Allows validation of the connection state transition. Parameters: from, to (connection state). Typically throws
     * IllegalStateException or UnsupportedOperationException if validation fails
     */
    public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_)
    {
        if (p_147232_2_ != EnumConnectionState.LOGIN && p_147232_2_ != EnumConnectionState.STATUS)
        {
            throw new UnsupportedOperationException("Invalid state " + p_147232_2_);
        }
    }

    /**
     * For scheduled network tasks. Used in NetHandlerPlayServer to send keep-alive packets and in NetHandlerLoginServer
     * for a login-timeout
     */
    public void onNetworkTick() {}

    static final class SwitchEnumConnectionState
    {
        static final int[] field_151291_a = new int[EnumConnectionState.values().length];
        private static final String __OBFID = "CL_00001457";

        static
        {
            try
            {
                field_151291_a[EnumConnectionState.LOGIN.ordinal()] = 1;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                field_151291_a[EnumConnectionState.STATUS.ordinal()] = 2;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }
        }
    }
}
