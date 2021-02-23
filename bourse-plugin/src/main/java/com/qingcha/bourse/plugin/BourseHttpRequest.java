package com.qingcha.bourse.plugin;

import com.qingcha.bourse.commons.discovery.DiscoveryMateData;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

/**
 * @author qiqiang
 */
public class BourseHttpRequest extends HttpRequestWrapper {
    private final List<DiscoveryMateData> list;

    public BourseHttpRequest(HttpRequest request, List<DiscoveryMateData> discoveryMateData) {
        super(request);
        this.list = discoveryMateData;
    }

    @Override
    public URI getURI() {
        Random random = new Random();
        int i = random.nextInt(list.size());
        DiscoveryMateData discoveryMateData = list.get(i);
        try {
            return new URI("http://" + discoveryMateData.getIp() + ":" + discoveryMateData.getPort() + "/" + discoveryMateData.getValue());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return super.getURI();
    }
}