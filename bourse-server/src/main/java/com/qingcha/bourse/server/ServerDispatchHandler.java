package com.qingcha.bourse.server;

import com.qingcha.bourse.commons.discovery.DiscoveryException;
import com.qingcha.bourse.commons.discovery.PullServiceEvent;
import com.qingcha.bourse.commons.discovery.RegisterServiceEvent;
import com.qingcha.bourse.server.discovery.ServiceDiscoveryCenter;
import com.qingcha.bourse.server.event.BaseBourseEventProcessor;
import com.qingcha.bourse.commons.event.BourseEvent;
import com.qingcha.bourse.protocol.BourseProtocol;
import com.qingcha.bourse.protocol.MessageType;
import com.qingcha.bourse.server.discovery.DiscoveryEventProcessor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qiqiang
 */
@ChannelHandler.Sharable
public class ServerDispatchHandler extends SimpleChannelInboundHandler<BourseProtocol> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerDispatchHandler.class);
    private final BaseBourseEventProcessor baseBourseEventProcessor;
    private final BourseServerConfig bourseServerConfig;

    public ServerDispatchHandler(BourseServerConfig bourseServerConfig) {
        this.bourseServerConfig = bourseServerConfig;
        this.baseBourseEventProcessor = new BaseBourseEventProcessor();
        init();
    }

    private void init() {
        DiscoveryEventProcessor discoveryEventProcessor = new DiscoveryEventProcessor();
        baseBourseEventProcessor.registerProcessor(RegisterServiceEvent.class, discoveryEventProcessor);
        baseBourseEventProcessor.registerProcessor(PullServiceEvent.class, discoveryEventProcessor);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BourseProtocol msg) throws Exception {
        // 如果是客户端发过来的ping消息，则交给心跳处理器处理
        if (msg.getHeader().getType() == MessageType.PING) {
            ctx.fireChannelRead(msg);
        } else {
            byte[] body = msg.getBody();
            BourseEvent bourseEvent = null;
            switch (msg.getHeader().getType()) {
                case MessageType.DISCOVERY_REGISTER:
                    bourseEvent = bourseServerConfig.getCodec().read(body, RegisterServiceEvent.class);
                    break;
                case MessageType.DISCOVERY_PULL:
                    bourseEvent = bourseServerConfig.getCodec().read(body, PullServiceEvent.class);
                    break;
                default:
                    break;
            }
            BourseEventContext context = new BourseEventContext(ctx.executor(), ctx.channel());
            baseBourseEventProcessor.process(bourseEvent, context);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof DiscoveryException) {
            LOGGER.error("服务发现处理异常", cause);
        }
        cause.printStackTrace();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 服务剔除
        ServiceDiscoveryCenter.getInstance().remove(ctx.channel().remoteAddress().toString());
        super.channelInactive(ctx);
    }
}