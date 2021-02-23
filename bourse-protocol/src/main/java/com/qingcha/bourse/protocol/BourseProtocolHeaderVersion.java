package com.qingcha.bourse.protocol;

/**
 * 协议版本
 *
 * @author qiqiang
 */

public enum BourseProtocolHeaderVersion {
    /**
     * 协议版本V1
     */
    V1;

    public static String currentName() {
        BourseProtocolHeaderVersion[] values = BourseProtocolHeaderVersion.values();
        return values[values.length - 1].name();
    }
}
