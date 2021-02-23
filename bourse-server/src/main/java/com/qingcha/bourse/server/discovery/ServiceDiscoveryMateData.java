package com.qingcha.bourse.server.discovery;

import com.qingcha.bourse.commons.discovery.DiscoveryMateData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiqiang
 */
public class ServiceDiscoveryMateData {
    private final String serviceName;
    private List<DiscoveryMateData> instances;

    public ServiceDiscoveryMateData(String serviceName) {
        this.serviceName = serviceName;
        instances = new ArrayList<>();
    }

    public String getServiceName() {
        return serviceName;
    }

    public List<DiscoveryMateData> getInstances() {
        return instances;
    }

    public void setInstances(List<DiscoveryMateData> instances) {
        this.instances = instances;
    }
}