package com.qingcha.bourse.server;

import com.qingcha.bourse.protocol.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * 心跳处理器
 *
 * @author qiqiang
 */
public class ServerHeartBeatHandler extends SimpleChannelInboundHandler<BourseProtocol> {
    private final Logger logger = LoggerFactory.getLogger(ServerHeartBeatHandler.class);
    /**
     * 读超时次数
     */
    private int readTimeoutCount;
    private final BourseServerConfig bourseServerConfig;
    private Channel channel;

    public ServerHeartBeatHandler(BourseServerConfig bourseServerConfig) {
        this.bourseServerConfig = bourseServerConfig;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, BourseProtocol msg) throws Exception {
        readTimeoutCount = 0;
        // 如果是客户端发过来的ping消息
        logger.info("接收到" + ctx.channel().remoteAddress() + "的ping消息");
        sendPong();

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
        ctx.fireChannelActive();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        Channel channel = ctx.channel();
        SocketAddress socketAddress = channel.remoteAddress();
        IdleStateEvent event = (IdleStateEvent) evt;
        switch (event.state()) {
            case READER_IDLE:
                readTimeoutCount++;
                logger.warn("{}超时{}次", socketAddress.toString(), readTimeoutCount);
                break;
            case WRITER_IDLE:
            case ALL_IDLE:
            default:
                break;
        }
        if (readTimeoutCount >= bourseServerConfig.getMaxReadTimeoutCount()) {
            logger.warn("{}已有{}超时，服务端认定掉线，断开连接", socketAddress, readTimeoutCount);
            // 断开连接
            BourseProtocolHeader header = new BourseProtocolHeader();
            header.setVersion(BourseProtocolHeaderVersion.currentName());
            header.setType(MessageType.CLOSE_CONNECT);
            BourseProtocol protocol = BourseProtocolBuilder.builder(bourseServerConfig.getCodec())
                    .header(header).body("超时" + readTimeoutCount + "次，断开连接").build();
            channel.writeAndFlush(protocol);
            channel.close();
        }
    }

    private void sendPong() {
        BourseProtocolHeader header = new BourseProtocolHeader();
        header.setType(MessageType.PONG);
        BourseProtocolBuilder builder = BourseProtocolBuilder.builder(bourseServerConfig.getCodec())
                .header(header);
        BourseProtocol protocol = builder.build();
        channel.writeAndFlush(protocol);
    }
}