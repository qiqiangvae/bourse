package com.qingcha.bourse.commons.discovery;

/**
 * 注册服务的事件
 *
 * @author qiqiang
 */
public class PullServiceEvent implements DiscoveryEvent {
    private static final long serialVersionUID = 42L;
    private final String serviceName;

    public PullServiceEvent(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

}