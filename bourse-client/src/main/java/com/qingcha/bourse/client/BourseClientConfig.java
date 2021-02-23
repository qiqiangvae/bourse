package com.qingcha.bourse.client;


import com.qingcha.bourse.commons.codec.Codec;
import com.qingcha.bourse.commons.codec.HessianCodec;

import java.net.InetSocketAddress;

/**
 * 服务器配置
 *
 * @author qiqiang
 */
public class BourseClientConfig {
    private Codec codec = new HessianCodec();
    private InetSocketAddress inetSocketAddress;
    private int maxReadTimeoutCount = 3;


    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public void setInetSocketAddress(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
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