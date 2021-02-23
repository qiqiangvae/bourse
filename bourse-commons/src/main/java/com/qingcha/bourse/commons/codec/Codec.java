package com.qingcha.bourse.commons.codec;

/**
 * @author qiqiang
 */
public interface Codec {
    <T> T read(byte[] data, Class<T> clazz);

    byte[] write(Object obj);
}