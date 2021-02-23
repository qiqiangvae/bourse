package com.qingcha.bourse.plugin;

import com.qingcha.bourse.client.BourseClient;
import com.qingcha.bourse.client.discovery.ServiceDiscoveryCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author qiqiang
 */
@DependsOn("bourseClient")
public class RestTemplatePluginConfig {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ServiceDiscoveryCache serviceDiscoveryCache;

    @PostConstruct
    public void postConstruct() {
        restTemplate.getInterceptors().add(new RestTemplatePlugin(serviceDiscoveryCache));
    }

}