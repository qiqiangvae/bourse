package com.qingcha.bourse.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author qiqiang
 */
public class BourseServer {
    private final Logger logger = LoggerFactory.getLogger(BourseServer.class);
    private final ServerBootstrap serverBootstrap;
    private final EventLoopGroup boss;
    private final EventLoopGroup workers;
    private BourseServerConfig bourseServerConfig;

    public BourseServer(int port) {
        this(null, port);
    }

    public BourseServer(String host, int port) {
        this.serverBootstrap = new ServerBootstrap();
        this.boss = new NioEventLoopGroup(1);
        this.workers = new NioEventLoopGroup();
        this.bourseServerConfig = new BourseServerConfig();
        bourseServerConfig.setInetSocketAddress(inetSocketAddress(host, port));
        BourseServerConfigHolder.set(bourseServerConfig);
    }

    /**
     * 启动服务端
     */
    private void start() {
        try {
            serverBootstrap.group(boss, workers)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new BourseServerChildChannelInitializer(bourseServerConfig));
            InetSocketAddress inetSocketAddress = bourseServerConfig.getInetSocketAddress();
            ChannelFuture channelFuture = serverBootstrap.bind(inetSocketAddress);
            channelFuture.addListener((ChannelFutureListener) channelFuture1 -> {
                if (channelFuture1.isSuccess()) {
                    logger.info("服务端启动，地址[{}]", inetSocketAddress);
                }
            });
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.addListener((ChannelFutureListener) channelFuture12 -> {
                if (channelFuture12.isSuccess()) {
                    logger.info("服务端关闭");
                }
            });
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            workers.shutdownGracefully();
        }
    }

    private InetSocketAddress inetSocketAddress(String host, int port) {
        if (StringUtils.isBlank(host)) {
            return new InetSocketAddress(port);
        } else {
            return new InetSocketAddress(host, port);
        }
    }

    public BourseServerConfig getBourseServerConfig() {
        return bourseServerConfig;
    }

    public void setBourseServerConfig(BourseServerConfig bourseServerConfig) {
        this.bourseServerConfig = bourseServerConfig;
    }

    public static void main(String[] args) {
        BourseServer bourseServer = new BourseServer(9999);
        bourseServer.start();
    }
}