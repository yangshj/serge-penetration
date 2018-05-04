package com.jiuqi.serge.penetration;

import java.net.InetSocketAddress;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

// 左边客户端
public class EchoClientHandler extends SimpleChannelInboundHandler<DatagramPacket>{
    
	// 阿里云服务器
	private static String IP = "47.104.207.160";
	
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet)
            throws Exception {
        //服务器推送对方IP和PORT
        ByteBuf buf = (ByteBuf) packet.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String str = new String(req, "UTF-8");
        String[] list = str.split(" ");
        //如果是A 则发送
        if(list[0].equals("A")){
            String ip = list[1];
            String port = list[2];
            ctx.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer("打洞信息".getBytes()), new InetSocketAddress(ip, Integer.parseInt(port))));
            Thread.sleep(1000);
            ctx.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer("P2P info..".getBytes()), new InetSocketAddress(ip, Integer.parseInt(port))));
        }
        System.out.println("接收到的信息:" + str);
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端向指定服务器(IP和PORT)发送L");
        ctx.writeAndFlush(new DatagramPacket(
                Unpooled.copiedBuffer("L".getBytes()), 
                new InetSocketAddress(IP, 7402)));
        super.channelActive(ctx);
    }
}