package com.qingcha.bourse.client.discovery;

import com.qingcha.bourse.client.BourseClientConfigHolder;
import com.qingcha.bourse.client.WriteableTask;
import com.qingcha.bourse.commons.codec.Codec;
import com.qingcha.bourse.commons.discovery.DiscoveryMateData;
import com.qingcha.bourse.commons.discovery.PullServiceEvent;
import com.qingcha.bourse.commons.discovery.RegisterServiceEvent;
import com.qingcha.bourse.protocol.*;
import io.netty.channel.Channel;

/**
 * @author qiqiang
 */
public class DiscoveryTask extends WriteableTask {
    public DiscoveryTask(Channel channel) {
        super(channel);
    }

    @Override
    public void run() {
        register();
        pull();
    }

    private void register() {
        Codec codec = BourseClientConfigHolder.get().getCodec();
        BourseProtocolHeader header = new BourseProtocolHeader();
        header.setVersion(BourseProtocolHeaderVersion.currentName());
        header.setType(MessageType.DISCOVERY_REGISTER);
        RegisterServiceEvent body = new RegisterServiceEvent();
        DiscoveryMateData discoveryMateData = new DiscoveryMateData();
        discoveryMateData.setIp("192.168.37.8");
        discoveryMateData.setServiceName("myService");
        discoveryMateData.setPort(8098);
        discoveryMateData.setValue("123");
        body.setDiscoveryMateData(discoveryMateData);
        BourseProtocol protocol = BourseProtocolBuilder.builder(codec)
                .header(header)
                .body(body).build();
        getChannel().writeAndFlush(protocol);
    }

    private void pull() {
        Codec codec = BourseClientConfigHolder.get().getCodec();
        BourseProtocolHeader header = new BourseProtocolHeader();
        header.setVersion(BourseProtocolHeaderVersion.currentName());
        header.setType(MessageType.DISCOVERY_PULL);
        PullServiceEvent body = new PullServiceEvent("myService");
        BourseProtocol protocol = BourseProtocolBuilder.builder(codec)
                .header(header)
                .body(body).build();
        getChannel().writeAndFlush(protocol);
    }
}