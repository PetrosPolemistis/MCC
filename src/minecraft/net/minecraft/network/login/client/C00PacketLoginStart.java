package net.minecraft.network.login.client;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.UUID;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;

public class C00PacketLoginStart extends Packet
{
    private GameProfile gameprofile;
    private static final String __OBFID = "CL_00001379";

    public C00PacketLoginStart() {}

    public C00PacketLoginStart(GameProfile gameprofile)
    {
        this.gameprofile = gameprofile;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException
    {
        this.gameprofile = new GameProfile((UUID)null, p_148837_1_.readStringFromBuffer(16));
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException
    {
        p_148840_1_.writeStringToBuffer(this.gameprofile.getName());
    }

    public void processPacket(INetHandlerLoginServer p_148833_1_)
    {
        p_148833_1_.processLoginStart(this);
    }

    public GameProfile getGameProfile()
    {
        return this.gameprofile;
    }

    public void processPacket(INetHandler p_148833_1_)
    {
        this.processPacket((INetHandlerLoginServer)p_148833_1_);
    }
}
