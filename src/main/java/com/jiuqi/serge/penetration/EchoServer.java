package com.jiuqi.serge.penetration;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class EchoServer {
    
    public static void main(String[] args) {
        Bootstrap b = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            b.group(group)
             .channel(NioDatagramChannel.class)
             .option(ChannelOption.SO_BROADCAST, true)
             .handler(new EchoServerHandler());
            
            b.bind(7402).sync().channel().closeFuture().await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            group.shutdownGracefully();
        }
        
    }
}