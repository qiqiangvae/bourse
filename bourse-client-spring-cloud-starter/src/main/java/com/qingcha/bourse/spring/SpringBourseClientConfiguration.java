package com.qingcha.bourse.spring;

import com.qingcha.bourse.client.BourseClient;
import com.qingcha.bourse.client.discovery.LocalServiceDiscoveryCache;
import com.qingcha.bourse.client.discovery.ServiceDiscoveryCache;
import com.qingcha.bourse.plugin.RestTemplatePluginConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qiqiang
 */
@Configuration
@ImportAutoConfiguration(classes = RestTemplatePluginConfig.class)
public class SpringBourseClientConfiguration {

    @Bean
    public ServiceDiscoveryCache serviceDiscoveryCache() {
        return new LocalServiceDiscoveryCache();
    }

    @Bean
    public BourseClient bourseClient() {
        BourseClient bourseClient = new BourseClient("localhost", 9999);
        bourseClient.setServiceDiscoveryCache(serviceDiscoveryCache());
        bourseClient.connect();
        return bourseClient;
    }
}