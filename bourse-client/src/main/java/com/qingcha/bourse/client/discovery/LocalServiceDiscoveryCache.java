package com.qingcha.bourse.client.discovery;

import com.qingcha.bourse.commons.discovery.DiscoveryMateData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiqiang
 */
public class LocalServiceDiscoveryCache implements ServiceDiscoveryCache {
    private final Map<String, List<DiscoveryMateData>> localCache;

    public LocalServiceDiscoveryCache() {
        localCache = new HashMap<>();
    }

    @Override
    public List<DiscoveryMateData> getService(String serviceName) {
        return localCache.getOrDefault(serviceName, new ArrayList<>());
    }

    @Override
    public void update(String serviceName, List<DiscoveryMateData> mateData) {
        localCache.put(serviceName, mateData);
    }
}