package com.qingcha.bourse.protocol;

import java.io.Serializable;

/**
 * @author qiqiang
 */
public class BourseProtocol implements Serializable {
    private static final long serialVersionUID = 42L;
    /**
     * 协议开始标记
     */
    public final int start = BourseProtocolConst.HEAD_START;
    /**
     * header 长度
     */
    private int headerLength;
    /**
     * header
     */
    private BourseProtocolHeader header;
    /**
     * body 长度
     */
    private int bodyLength = 0;
    /**
     * body
     */
    private byte[] body = new byte[0];

    public int getStart() {
        return start;
    }

    public int getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(int headerLength) {
        this.headerLength = headerLength;
    }

    public BourseProtocolHeader getHeader() {
        return header;
    }

    public void setHeader(BourseProtocolHeader header) {
        this.header = header;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}