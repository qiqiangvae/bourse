package com.qingcha.bourse.client;

import com.qingcha.bourse.client.discovery.LocalServiceDiscoveryCache;
import com.qingcha.bourse.client.discovery.ServiceDiscoveryCache;
import com.qingcha.bourse.commons.discovery.DiscoveryMateData;
import com.qingcha.bourse.commons.discovery.ServiceDiscoveryMateDataWrapper;
import com.qingcha.bourse.protocol.BourseProtocol;
import com.qingcha.bourse.protocol.BourseProtocolConst;
import com.qingcha.bourse.protocol.BourseProtocolHeader;
import com.qingcha.bourse.protocol.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;

/**
 * @author qiqiang
 */
public class ClientDispatchHandler extends SimpleChannelInboundHandler<BourseProtocol> {
    private ServiceDiscoveryCache serviceDiscoveryCache;

    public ClientDispatchHandler(ServiceDiscoveryCache serviceDiscoveryCache) {
        this.serviceDiscoveryCache = serviceDiscoveryCache;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BourseProtocol msg) throws Exception {
        BourseProtocolHeader header = msg.getHeader();
        if (header.getType() == MessageType.PONG) {
            ctx.fireChannelRead(msg);
        } else {
            byte[] body = msg.getBody();
            if (header.getType() == MessageType.DISCOVERY_PULL && header.getRequest() == BourseProtocolConst.RESPONSE) {
                ServiceDiscoveryMateDataWrapper wrapper = BourseClientConfigHolder.get().getCodec().read(body, ServiceDiscoveryMateDataWrapper.class);
                serviceDiscoveryCache.update(wrapper.getServiceName(), wrapper.getInstances());
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}