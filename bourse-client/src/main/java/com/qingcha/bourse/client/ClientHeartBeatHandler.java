package com.qingcha.bourse.client;

import com.qingcha.bourse.protocol.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author qiqiang
 */
public class ClientHeartBeatHandler extends SimpleChannelInboundHandler<BourseProtocol> implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(ClientHeartBeatHandler.class);

    private EventExecutor executor;
    private Channel channel;
    private final Runnable inactiveTask;
    private final ClientStartedCallback activeTask;
    private final BourseClientConfig bourseClientConfig;

    public ClientHeartBeatHandler(Runnable inactiveTask, ClientStartedCallback activeTask) {
        this.inactiveTask = inactiveTask;
        this.activeTask = activeTask;
        bourseClientConfig = BourseClientConfigHolder.get();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, BourseProtocol msg) throws Exception {
        schedule();
    }

    @Override
    public void run() {
        sendPing();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
        executor = ctx.executor();
        schedule();
        ctx.fireChannelActive();
        activeTask.setChannelHandlerContext(ctx);
        ctx.executor().execute(activeTask);
    }

    private void schedule() {
        executor.schedule(this, 3, TimeUnit.SECONDS);
    }

    private void sendPing() {

        BourseProtocolHeader header = new BourseProtocolHeader();
        header.setType(MessageType.PING);
        header.setVersion(BourseProtocolHeaderVersion.currentName());
        BourseProtocolBuilder builder = BourseProtocolBuilder.builder(bourseClientConfig.getCodec())
                .header(header);
        BourseProtocol protocol = builder.build();
        channel.writeAndFlush(protocol);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 回调连接不活跃事件
        ctx.executor().execute(inactiveTask);
    }
}