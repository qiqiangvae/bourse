package com.qingcha.bourse.plugin;

import com.qingcha.bourse.client.discovery.ServiceDiscoveryCache;
import com.qingcha.bourse.commons.discovery.DiscoveryMateData;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qiqiang
 */
public class RestTemplatePlugin implements ClientHttpRequestInterceptor {
    private final ServiceDiscoveryCache serviceDiscoveryCache;

    public RestTemplatePlugin(ServiceDiscoveryCache serviceDiscoveryCache) {
        this.serviceDiscoveryCache = serviceDiscoveryCache;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        URI uri = request.getURI();
        String path = uri.getPath();
        path = path.substring(1);
        List<DiscoveryMateData> list = serviceDiscoveryCache.getService(path)
                .stream().filter(DiscoveryMateData::isHealth).collect(Collectors.toList());
        if (list.size() > 0) {
            request = new BourseHttpRequest(request, list);
        }
        return execution.execute(request, body);
    }
}