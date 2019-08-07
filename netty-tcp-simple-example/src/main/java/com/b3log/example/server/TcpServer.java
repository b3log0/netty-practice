package com.b3log.example.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

import java.net.InetSocketAddress;

public class TcpServer {

    private EventLoopGroup boss;
    private EventLoopGroup work;
    private Integer port;
    private String host;

    public TcpServer(Integer port, String host) {
        this.port = port;
        this.host = host;
    }

    public void startServer()throws Exception{
        ServerBootstrap sb = new ServerBootstrap();
        boss = new NioEventLoopGroup(10);
        work = new NioEventLoopGroup(10);
        sb.channel(NioServerSocketChannel.class).group(boss,work).option(ChannelOption.SO_REUSEADDR,true)
        .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast("bytesDecoder", new ByteArrayDecoder());
                ch.pipeline().addLast("bytesEncoder", new ByteArrayEncoder());
                ch.pipeline().addLast(new ServerHandler());
            }
        });
        sb.bind(new InetSocketAddress(host,port)).sync();
    }


}
