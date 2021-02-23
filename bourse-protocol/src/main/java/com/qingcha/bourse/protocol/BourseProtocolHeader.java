package com.qingcha.bourse.protocol;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author qiqiang
 */
public class BourseProtocolHeader implements Serializable {
    private static final long serialVersionUID = 42L;
    /**
     * 请求id
     */
    private String id = UUID.randomUUID().toString().replace("-", "");
    /**
     * 版本号
     */
    private String version = BourseProtocolHeaderVersion.currentName();
    /**
     * 消息类型
     */
    private int type;
    /**
     * 1请求0响应
     */
    private int request = BourseProtocolConst.REQUEST;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }
}