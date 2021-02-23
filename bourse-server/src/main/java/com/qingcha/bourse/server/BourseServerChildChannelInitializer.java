package com.qingcha.bourse.server;

import com.qingcha.bourse.commons.codec.Codec;
import com.qingcha.bourse.protocol.ByteToProtocolDecoder;
import com.qingcha.bourse.protocol.ProtocolToByteEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author qiqiang
 */
public class BourseServerChildChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final BourseServerConfig bourseServerConfig;
    private final ServerDispatchHandler serverDispatchHandler;

    public BourseServerChildChannelInitializer(BourseServerConfig bourseServerConfig) {
        this.bourseServerConfig = bourseServerConfig;
        serverDispatchHandler = new ServerDispatchHandler(bourseServerConfig);
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        Codec codec = bourseServerConfig.getCodec();
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ByteToProtocolDecoder(codec));
        pipeline.addLast(new ProtocolToByteEncoder(codec));
        pipeline.addLast(new IdleStateHandler(bourseServerConfig.getReaderIdleTimeSeconds(), 0, 0));
        pipeline.addLast(serverDispatchHandler);
        pipeline.addLast(new ServerHeartBeatHandler(bourseServerConfig));
    }
}