package com.qingcha.bourse.client;

import io.netty.channel.Channel;

/**
 * @author qiqiang
 */
public abstract class WriteableTask implements Runnable {
    private final Channel channel;

    protected WriteableTask(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }
}