package mcheese.hooks;

import java.net.InetAddress;
import java.net.SocketAddress;

import org.apache.commons.lang3.ArrayUtils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.GenericFutureListener;
import mcheese.MCheese;
import mcheese.events.PacketDispatchEvent;
import mcheese.events.PacketProcessEvent;
import mcheese.events.PacketSendEvent;
import mcheese.events.PostPacketSendEvent;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;

@io.netty.channel.ChannelHandler.Sharable
public class NetworkManagerHook extends NetworkManager
{
	
	public NetworkManagerHook(boolean clientSide) 
	{
		super(clientSide);
		System.out.println("INITIALIZED: " + this.getClass().getSimpleName());
	}
	@Override
	public void sendPacket(Packet p)
	{
        //TODO: MCheese
        PacketSendEvent event = new PacketSendEvent(p);
        MCheese.theClient.getEventManager().callEvent(event);
        
        if(this.channel != null && this.channel.isOpen())
        {
            if(event.isCancelled())
            {
            	return;
            }
            this.flushOutboundQueue();
            this.dispatchPacket(event.getOutboundPacket(), null);
        }
        else
        {
        	this.outboundPacketsQueue.add(new NetworkManager.InboundHandlerTuplePacketListener(event.getOutboundPacket(), new GenericFutureListener[0]));
        }
        //TODO: MCheese
        PostPacketSendEvent event2 = new PostPacketSendEvent(p);
        MCheese.theClient.getEventManager().callEvent(event2);
	}
	@Override
	public void scheduleOutboundPacket(Packet p, GenericFutureListener... listeners)
	{
        //TODO: MCheese
        PacketSendEvent event = new PacketSendEvent(p);
        MCheese.theClient.getEventManager().callEvent(event);
        
        if(event.isCancelled())
        {
        	return;
        }
        
        super.scheduleOutboundPacket(event.getOutboundPacket(), listeners);
	}
	
	@Override
	public void processReceivedPackets()
	{
        //TODO: MCheese
    	PacketProcessEvent event2 = null;
    	if(MCheese.MC.thePlayer != null)
    	{
    		event2 = new PacketProcessEvent(this.receivedPacketsQueue, this.netHandler);
    		MCheese.theClient.getEventManager().callEvent(event2);
    	}
    	if(event2 != null && event2.isCancelled())
    	{
    		return;
    	}
    	
    	super.processReceivedPackets();
	}
    protected void dispatchPacket(final Packet p_150732_1_, final GenericFutureListener[] p_150732_2_)
    {
        
        final EnumConnectionState var3 = EnumConnectionState.func_150752_a(p_150732_1_);
        final EnumConnectionState var4 = (EnumConnectionState)this.channel.attr(attrKeyConnectionState).get();

        if (var4 != var3)
        {
            logger.debug("Disabled auto read");
            this.channel.config().setAutoRead(false);
        }

        if (this.channel.eventLoop().inEventLoop())
        {
            if (var3 != var4)
            {
                this.setConnectionState(var3);
            }
            
            //TODO: MCheese
            PacketDispatchEvent event = new PacketDispatchEvent(p_150732_1_);
            MCheese.theClient.getEventManager().callEvent(event);
            if(!event.isCancelled())
            {
	            this.channel.writeAndFlush(p_150732_1_).addListeners(p_150732_2_).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            }
        }
        else
        {
            this.channel.eventLoop().execute(new Runnable()
            {
                private static final String __OBFID = "CL_00001241";
                public void run()
                {
                    if (var3 != var4)
                    {
                        NetworkManagerHook.this.setConnectionState(var3);
                    }
                    //TODO: MCheese
                    PacketDispatchEvent event = new PacketDispatchEvent(p_150732_1_);
                    MCheese.theClient.getEventManager().callEvent(event);
                    if(!event.isCancelled())
                    {
                    	NetworkManagerHook.this.channel.writeAndFlush(p_150732_1_).addListeners(p_150732_2_).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                    }
                }
            });
        }
    }
    public static NetworkManagerHook provideLocalClient(SocketAddress p_150722_0_)
    {
        final NetworkManagerHook var1 = new NetworkManagerHook(true);
        ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group(eventLoops)).handler(new ChannelInitializer()
        {
            private static final String __OBFID = "CL_00001243";
            protected void initChannel(Channel p_initChannel_1_)
            {
                p_initChannel_1_.pipeline().addLast("packet_handler", var1);
            }
        })).channel(LocalChannel.class)).connect(p_150722_0_).syncUninterruptibly();
        return var1;
    }
    public static NetworkManagerHook provideLanClient(InetAddress address, int port)
    {    	
        final NetworkManagerHook var2 = new NetworkManagerHook(true);
        ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group(eventLoops)).handler(new ChannelInitializer()
        {
            private static final String __OBFID = "CL_00001242";
            protected void initChannel(Channel channel)
            {
                try
                {
                    channel.config().setOption(ChannelOption.IP_TOS, Integer.valueOf(24));
                }
                catch (ChannelException var4)
                {
                    ;
                }

                try
                {
                    channel.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(false));
                }
                catch (ChannelException var3)
                {
                    ;
                }

                channel.pipeline().addLast("timeout", new ReadTimeoutHandler(20)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer(NetworkManager.field_152462_h)).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer(NetworkManager.field_152462_h)).addLast("packet_handler", var2);
            }
        })).channel(NioSocketChannel.class)).connect(address, port).syncUninterruptibly();
        return var2;
    }
    //TODO: MCheese addition! :D
    public static NetworkManagerHook provideLanClient(InetAddress address, int port, InetAddress bindaddress, int bindport)
    {
        final NetworkManagerHook var2 = new NetworkManagerHook(true);
        Bootstrap bootstrap = ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group(eventLoops)).handler(new ChannelInitializer()
        {
            private static final String __OBFID = "CL_00001242";
            protected void initChannel(Channel channel)
            {
                try
                {
                    channel.config().setOption(ChannelOption.IP_TOS, Integer.valueOf(24));
                }
                catch (ChannelException var4)
                {
                    ;
                }

                try
                {
                    channel.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(false));
                }
                catch (ChannelException var3)
                {
                    ;
                }
                
                channel.pipeline().addLast("timeout", new ReadTimeoutHandler(20)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer(NetworkManager.field_152462_h)).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer(NetworkManager.field_152462_h)).addLast("packet_handler", var2);
            }
        })).channel(NioSocketChannel.class));
        
        bootstrap.bind(bindaddress, bindport);
        bootstrap.connect(address, port).syncUninterruptibly();
        
        return var2;
    }
}
