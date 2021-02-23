package com.qingcha.bourse.commons.discovery;

/**
 * 注册服务的事件
 *
 * @author qiqiang
 */
public class RegisterServiceEvent implements DiscoveryEvent {
    private static final long serialVersionUID = 42L;
    private DiscoveryMateData discoveryMateData;

    public DiscoveryMateData getDiscoveryMateData() {
        return discoveryMateData;
    }

    public void setDiscoveryMateData(DiscoveryMateData discoveryMateData) {
        this.discoveryMateData = discoveryMateData;
    }
}