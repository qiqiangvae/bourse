package com.qingcha.bourse.server.discovery;

import com.qingcha.bourse.commons.discovery.DiscoveryMateData;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务发现中心
 *
 * @author qiqiang
 */
public class ServiceDiscoveryCenter {

    private final Map<String, ServiceDiscoveryMateData> serviceMap;
    private final Map<String, List<DiscoveryMateData>> ipServiceMap;

    private ServiceDiscoveryCenter() {
        serviceMap = new HashMap<>();
        ipServiceMap = new HashMap<>();
    }

    /**
     * 获取单例
     *
     * @return ServiceDiscoveryCenter
     */
    public static ServiceDiscoveryCenter getInstance() {
        return ServiceDiscoveryCenterHolder.INSTANCE.instance;
    }

    /**
     * 保存服务发现示例
     *
     * @param discoveryMateData 服务发现元数据
     */
    public void saveService(DiscoveryMateData discoveryMateData) {
        if (discoveryMateData == null) {
            return;
        }
        String serviceName = discoveryMateData.getServiceName();
        if (StringUtils.isBlank(serviceName)) {
            return;
        }
        ServiceDiscoveryMateData serviceDiscoveryMateData = serviceMap.get(serviceName);
        if (serviceDiscoveryMateData == null) {
            serviceDiscoveryMateData = new ServiceDiscoveryMateData(serviceName);
            serviceMap.put(serviceName, serviceDiscoveryMateData);
        }
        List<DiscoveryMateData> ipList = ipServiceMap.getOrDefault(discoveryMateData.getIp(), new ArrayList<>());
        List<DiscoveryMateData> instances = serviceDiscoveryMateData.getInstances();
        boolean exist = false;
        for (DiscoveryMateData instance : instances) {
            if (instance.uniqueId().equals(discoveryMateData.uniqueId())) {
                instance.copy(discoveryMateData);
                exist = true;
                break;
            }
        }
        // 如果不存在，则新增
        if (!exist) {
            instances.add(discoveryMateData);
        }
    }

    public void remove(String ip) {
        ipServiceMap.remove(ip);
    }

    public ServiceDiscoveryMateData getService(String service) {
        return serviceMap.get(service);
    }

    enum ServiceDiscoveryCenterHolder {
        /**
         * 单例
         */
        INSTANCE;
        private final ServiceDiscoveryCenter instance;

        ServiceDiscoveryCenterHolder() {
            instance = new ServiceDiscoveryCenter();
        }
    }
}