package com.qingcha.bourse.server;

/**
 * @author qiqiang
 */
public class BourseServerConfigHolder {
    private static BourseServerConfig CONFIG;

    public static BourseServerConfig get() {
        return CONFIG;
    }

    public static void set(BourseServerConfig CONFIG) {
        BourseServerConfigHolder.CONFIG = CONFIG;
    }
}