package com.qingcha.bourse.client;

import com.qingcha.bourse.client.discovery.ServiceDiscoveryCache;
import com.qingcha.bourse.commons.codec.HessianCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author qiqiang
 */
public class BourseClient {
    private final Logger logger = LoggerFactory.getLogger(BourseClient.class);

    private Bootstrap bootstrap;
    private EventLoopGroup group;
    private volatile boolean connected = false;
    private volatile Channel channel;
    private final BourseClientChannelInitializer clientChannelInitializer;
    private final BourseClientConfig bourseClientConfig;

    public BourseClient(String host, int port) {
        this.bourseClientConfig = new BourseClientConfig();
        BourseClientConfigHolder.set(bourseClientConfig);
        bourseClientConfig.setInetSocketAddress(inetSocketAddress(host, port));
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();
        clientChannelInitializer = new BourseClientChannelInitializer(this);
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(clientChannelInitializer);
    }


    public void connect() {
        ChannelFuture channelFuture = bootstrap.connect(bourseClientConfig.getInetSocketAddress());
        channel = channelFuture.channel();
        channelFuture.addListener((ChannelFutureListener) future -> {
            if (connected = future.isSuccess()) {
                logger.info("连接服务器成功");
            } else {
                logger.info("连接失败，正在重新连接…………");
                Thread.sleep(2000);
                channel.eventLoop().execute(this::connect);
            }
        });
    }

    public void setServiceDiscoveryCache(ServiceDiscoveryCache serviceDiscoveryCache) {
        clientChannelInitializer.setServiceDiscoveryCache(serviceDiscoveryCache);
    }

    private InetSocketAddress inetSocketAddress(String host, int port) {
        if (StringUtils.isBlank(host)) {
            return new InetSocketAddress(port);
        } else {
            return new InetSocketAddress(host, port);
        }
    }

    private void destroy() {
        logger.info("客户端停止运行");
        group.shutdownGracefully();
    }


    public static void main(String[] args) {
        BourseClient bourseClient = new BourseClient("localhost", 9999);
        bourseClient.connect();
    }
}