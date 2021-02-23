package com.qingcha.bourse.client;

import com.qingcha.bourse.client.config.ConfigTask;
import com.qingcha.bourse.client.discovery.DiscoveryTask;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.TimeUnit;

/**
 * @author qiqiang
 */
public class ClientStartedCallback implements Runnable {
    private ChannelHandlerContext channelHandlerContext;

    @Override
    public void run() {
        DiscoveryTask discoveryTask = new DiscoveryTask(channelHandlerContext.channel());
        channelHandlerContext.executor().scheduleAtFixedRate(discoveryTask, 0,10, TimeUnit.SECONDS);
        ConfigTask configTask = new ConfigTask();
        channelHandlerContext.executor().scheduleAtFixedRate(configTask, 0,10, TimeUnit.SECONDS);
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }
}