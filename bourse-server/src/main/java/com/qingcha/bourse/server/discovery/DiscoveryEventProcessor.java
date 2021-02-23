package com.qingcha.bourse.server.discovery;

import com.qingcha.bourse.commons.discovery.DiscoveryEvent;
import com.qingcha.bourse.commons.discovery.PullServiceEvent;
import com.qingcha.bourse.commons.discovery.RegisterServiceEvent;
import com.qingcha.bourse.server.BourseEventContext;
import com.qingcha.bourse.server.event.BourseEventProcessor;

/**
 * @author qiqiang
 */
public class DiscoveryEventProcessor implements BourseEventProcessor<DiscoveryEvent> {

    private final RegisterDiscoveryEventProcessor registerDiscoveryEventProcessor;
    private final PullServiceEventProcessor pullServiceEventProcessor;

    public DiscoveryEventProcessor() {
        this.registerDiscoveryEventProcessor = new RegisterDiscoveryEventProcessor();
        this.pullServiceEventProcessor = new PullServiceEventProcessor();
    }

    @Override
    public void process(DiscoveryEvent event, BourseEventContext context) {
        if (event instanceof RegisterServiceEvent) {
            registerDiscoveryEventProcessor.process((RegisterServiceEvent) event, context);
        } else if (event instanceof PullServiceEvent) {
            pullServiceEventProcessor.process((PullServiceEvent) event, context);
        }
    }
}