package com.b3log.example.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;

public class ServerHandler extends SimpleChannelInboundHandler<byte[]> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client connected");
    }

    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        return true;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, byte[] bytes) throws Exception {
        InetSocketAddress address = (InetSocketAddress) channelHandlerContext.channel().localAddress();
        System.out.println("receive message from client:"+address.getHostName()+",message:"+new String(bytes));
        channelHandlerContext.writeAndFlush(bytes);
    }

    @Override
    public boolean isSharable() {
        return Boolean.TRUE;
    }
}
