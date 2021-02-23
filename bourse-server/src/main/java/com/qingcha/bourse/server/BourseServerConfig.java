package com.qingcha.bourse.server;


import com.qingcha.bourse.commons.codec.Codec;
import com.qingcha.bourse.commons.codec.HessianCodec;

import java.net.InetSocketAddress;

/**
 * 服务器配置
 *
 * @author qiqiang
 */
public class BourseServerConfig {
    private Codec codec = new HessianCodec();
    private InetSocketAddress inetSocketAddress;
    private int maxReadTimeoutCount = 3;
    private int readerIdleTimeSeconds = 5;

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public void setInetSocketAddress(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    public int getReaderIdleTimeSeconds() {
        return readerIdleTimeSeconds;
    }

    public void setReaderIdleTimeSeconds(int readerIdleTimeSeconds) {
        this.readerIdleTimeSeconds = readerIdleTimeSeconds;
    }

    public int getMaxReadTimeoutCount() {
        return maxReadTimeoutCount;
    }

    public void setMaxReadTimeoutCount(int maxReadTimeoutCount) {
        this.maxReadTimeoutCount = maxReadTimeoutCount;
    }

    public Codec getCodec() {
        return codec;
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }
}