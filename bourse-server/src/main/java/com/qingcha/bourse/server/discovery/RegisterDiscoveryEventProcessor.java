package com.qingcha.bourse.server.discovery;

import com.qingcha.bourse.commons.discovery.DiscoveryException;
import com.qingcha.bourse.commons.discovery.DiscoveryMateData;
import com.qingcha.bourse.commons.discovery.RegisterServiceEvent;
import com.qingcha.bourse.server.BourseEventContext;
import com.qingcha.bourse.server.event.BourseEventProcessor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author qiqiang
 */
public class RegisterDiscoveryEventProcessor implements BourseEventProcessor<RegisterServiceEvent> {

    @Override
    public void process(RegisterServiceEvent event, BourseEventContext context) {
        DiscoveryMateData discoveryMateData = event.getDiscoveryMateData();
        if (StringUtils.isBlank(discoveryMateData.getServiceName())) {
            throw new DiscoveryException("服务名称不能为空");
        }
        if (StringUtils.isBlank(discoveryMateData.getIp())) {
            throw new DiscoveryException("ip不能为空");
        }
        if (discoveryMateData.getPort() == 0) {
            throw new DiscoveryException("port非法");
        }
        if (StringUtils.isBlank(discoveryMateData.getValue())) {
            throw new DiscoveryException("服务值不能为空");
        }
        discoveryMateData.setHealth(true);
        context.getExecutor().execute(() -> ServiceDiscoveryCenter.getInstance().saveService(discoveryMateData));
    }
}