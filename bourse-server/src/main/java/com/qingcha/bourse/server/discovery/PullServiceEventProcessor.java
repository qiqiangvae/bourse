package com.qingcha.bourse.server.discovery;

import com.qingcha.bourse.commons.discovery.PullServiceEvent;
import com.qingcha.bourse.commons.discovery.ServiceDiscoveryMateDataWrapper;
import com.qingcha.bourse.protocol.*;
import com.qingcha.bourse.server.BourseEventContext;
import com.qingcha.bourse.server.BourseServerConfig;
import com.qingcha.bourse.server.BourseServerConfigHolder;
import com.qingcha.bourse.server.event.BourseEventProcessor;

/**
 * @author qiqiang
 */
public class PullServiceEventProcessor implements BourseEventProcessor<PullServiceEvent> {

    @Override
    public void process(PullServiceEvent event, BourseEventContext context) {
        context.getExecutor().execute(() -> {
            ServiceDiscoveryMateData service = ServiceDiscoveryCenter.getInstance().getService(event.getServiceName());
            BourseProtocolHeader header = new BourseProtocolHeader();
            header.setRequest(BourseProtocolConst.RESPONSE);
            header.setType(MessageType.DISCOVERY_PULL);
            ServiceDiscoveryMateDataWrapper wrapper = new ServiceDiscoveryMateDataWrapper(service.getServiceName());
            wrapper.setInstances(service.getInstances());
            BourseProtocol protocol = BourseProtocolBuilder.builder(BourseServerConfigHolder.get().getCodec())
                    .header(header).body(wrapper).build();
            context.getChannel().writeAndFlush(protocol);
        });
    }
}