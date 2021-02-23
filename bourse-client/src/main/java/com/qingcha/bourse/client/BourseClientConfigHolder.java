package com.qingcha.bourse.client;

/**
 * @author qiqiang
 */
public class BourseClientConfigHolder {
    private static BourseClientConfig CONFIG;

    public static BourseClientConfig get() {
        return CONFIG;
    }

    public static void set(BourseClientConfig CONFIG) {
        BourseClientConfigHolder.CONFIG = CONFIG;
    }
}