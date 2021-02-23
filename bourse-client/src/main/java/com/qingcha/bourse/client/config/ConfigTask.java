package com.qingcha.bourse.client.config;

/**
 * @author qiqiang
 */
public class ConfigTask implements Runnable{
    @Override
    public void run() {
        System.out.println("拉取配置信息");
    }
}