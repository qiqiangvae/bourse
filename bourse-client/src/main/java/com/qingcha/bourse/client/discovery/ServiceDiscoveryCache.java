package com.qingcha.bourse.client.discovery;

import com.qingcha.bourse.commons.discovery.DiscoveryMateData;

import java.util.List;

/**
 * @author qiqiang
 */
public interface ServiceDiscoveryCache {
    List<DiscoveryMateData> getService(String serviceName);

    void update(String serviceName, List<DiscoveryMateData> mateData);
}