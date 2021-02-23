package com.qingcha.bourse.commons.discovery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qiqiang
 */
public class ServiceDiscoveryMateDataWrapper implements Serializable {
    private static final long serialVersionUID = 42L;
    private final String serviceName;
    private List<DiscoveryMateData> instances;

    public ServiceDiscoveryMateDataWrapper(String serviceName) {
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