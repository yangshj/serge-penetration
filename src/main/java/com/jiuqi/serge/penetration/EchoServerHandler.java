package com.jiuqi.serge.penetration;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetSocketAddress;

import com.alibaba.fastjson.JSON;

public class EchoServerHandler extends SimpleChannelInboundHandler<DatagramPacket>{

    boolean flag = false;
    InetSocketAddress addr1 = null;
    InetSocketAddress addr2 = null;
    /**
     * channelRead0 是对每个发送过来的UDP包进行处理
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet)
            throws Exception {
        ByteBuf buf = (ByteBuf) packet.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String str = new String(req, "UTF-8");
        System.out.println("-------------"+str);
        if(str.equalsIgnoreCase("L")){
            //保存到addr1中 并发送addr2
            addr1 = packet.sender();
            System.out.println("L 命令， 保存到addr1中 "+JSON.toJSONString(addr1));
            changeFlag();
        }else if(str.equalsIgnoreCase("R")){
            //保存到addr2中 并发送addr1
            addr2 = packet.sender();
            System.out.println("R 命令， 保存到addr2中 "+JSON.toJSONString(addr2));
            changeFlag();
        }
        if(flag){
            //addr1 -> addr2
            String remot = "A " + addr2.getAddress().toString().replace("/", "")
                    +" "+addr2.getPort();
            ctx.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer(remot.getBytes()), addr1));
            //addr2 -> addr1
            remot = "A " + addr1.getAddress().toString().replace("/", "")
                    +" "+addr1.getPort();
            ctx.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer(remot.getBytes()), addr2));
            System.out.println("服务器交换信息IP和端口信息");
        }
        
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务器启动...");
        super.channelActive(ctx);
    }
    
    // 判断是否可以交换信息
    private void changeFlag(){
    	if(addr1!=null && addr2!=null){
    		flag = true;
    	}
    }
}