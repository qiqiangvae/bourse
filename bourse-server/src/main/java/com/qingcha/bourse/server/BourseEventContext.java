package com.qingcha.bourse.server;

import io.netty.channel.Channel;

import java.util.concurrent.ExecutorService;

/**
 * @author qiqiang
 */
public class BourseEventContext {
    private ExecutorService executor;
    private Channel channel;

    public BourseEventContext(ExecutorService executorService, Channel channel) {
        this.executor = executorService;
        this.channel = channel;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}