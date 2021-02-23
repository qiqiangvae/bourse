package com.qingcha.bourse.client;

import com.qingcha.bourse.client.discovery.ServiceDiscoveryCache;
import com.qingcha.bourse.commons.codec.Codec;
import com.qingcha.bourse.protocol.ByteToProtocolDecoder;
import com.qingcha.bourse.protocol.ProtocolToByteEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author qiqiang
 */
public class BourseClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final BourseClient bourseClient;
    private ServiceDiscoveryCache serviceDiscoveryCache;

    public BourseClientChannelInitializer(BourseClient bourseClient) {
        this.bourseClient = bourseClient;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        Codec codec = BourseClientConfigHolder.get().getCodec();
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ByteToProtocolDecoder(codec));
        pipeline.addLast(new ProtocolToByteEncoder(codec));
        pipeline.addLast(new ClientDispatchHandler(serviceDiscoveryCache));
        pipeline.addLast(new ClientHeartBeatHandler(bourseClient::connect, new ClientStartedCallback()));
    }

    public void setServiceDiscoveryCache(ServiceDiscoveryCache serviceDiscoveryCache) {
        this.serviceDiscoveryCache = serviceDiscoveryCache;
    }
}