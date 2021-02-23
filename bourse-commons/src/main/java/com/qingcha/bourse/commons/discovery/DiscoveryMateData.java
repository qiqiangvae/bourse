package com.qingcha.bourse.commons.discovery;

import java.io.Serializable;

/**
 * 服务发现元数据
 *
 * @author qiqiang
 */
public class DiscoveryMateData implements Serializable {
    private static final long serialVersionUID = 42L;
    private String serviceName;
    private String ip;
    private int port;
    private boolean health;
    private String value;

    public String uniqueId() {
        return String.format("%s&%s:%d", serviceName, ip, port);
    }

    /**
     * 复制属性，排除serviceName，ip,port
     *
     * @param source 数据来源
     */
    public void copy(DiscoveryMateData source) {
        this.health = source.health;
        this.value = source.value;
    }

    @Override
    public String toString() {
        return "DiscoveryMateData{" +
                "serviceName='" + serviceName + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", health=" + health +
                ", value='" + value + '\'' +
                '}';
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isHealth() {
        return health;
    }

    public void setHealth(boolean health) {
        this.health = health;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}