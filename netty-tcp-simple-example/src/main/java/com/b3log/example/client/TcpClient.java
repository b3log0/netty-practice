package com.b3log.example.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class TcpClient {

    private EventLoopGroup group;
    private Channel clientChannel;
    private String host;
    private Integer port;

    public TcpClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public TcpClient startClient()throws Exception{

        group = new NioEventLoopGroup(5);
        Bootstrap b = new Bootstrap();

        b.channel(NioSocketChannel.class)
                .group(group)
                .handler(new ClientHandler());
        ChannelFuture f = b.connect(new InetSocketAddress(host,port)).sync();
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (f.isSuccess()) {
                    System.out.println("连接服务器成功");
                    clientChannel = f.channel();
                } else {
                    System.out.println("连接服务器失败");
                    f.cause().printStackTrace();
                    group.shutdownGracefully(); //关闭线程组
                }
            }
        });
        return this;
    }
    public void sendMsg(String message){
        clientChannel.writeAndFlush(message.getBytes());
    }
}
