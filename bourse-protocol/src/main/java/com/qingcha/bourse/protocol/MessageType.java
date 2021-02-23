package com.qingcha.bourse.protocol;

/**
 * @author qiqiang
 */
public interface MessageType {
    int PING = 101;
    int PONG = 102;
    /**
     * 断开连接
     */
    int CLOSE_CONNECT = 201;
    /**
     * 服务发现
     */
    int DISCOVERY = 300;
    int DISCOVERY_REGISTER = 301;
    int DISCOVERY_PULL = 302;
    /**
     * 配置中心
     */
    int CONFIG = 401;

}