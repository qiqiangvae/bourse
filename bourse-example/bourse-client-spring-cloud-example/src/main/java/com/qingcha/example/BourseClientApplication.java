package com.qingcha.example;

import com.qingcha.bourse.spring.EnableBourseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author qiqiang
 */
@SpringBootApplication
@EnableBourseClient
public class BourseClientApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BourseClientApplication.class);
        RestTemplate bean = context.getBean(RestTemplate.class);
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(2000);
                bean.getForEntity("myService", String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}