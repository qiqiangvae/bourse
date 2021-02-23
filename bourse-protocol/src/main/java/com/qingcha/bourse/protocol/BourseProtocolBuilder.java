package com.qingcha.bourse.protocol;


import com.qingcha.bourse.commons.codec.Codec;

/**
 * @author qiqiang
 */
public class BourseProtocolBuilder {
    private BourseProtocol bourseProtocol;
    private Codec codec;

    public static BourseProtocolBuilder builder(Codec codec) {
        BourseProtocolBuilder bourseProtocolBuilder = new BourseProtocolBuilder();
        bourseProtocolBuilder.bourseProtocol = new BourseProtocol();
        bourseProtocolBuilder.codec = codec;
        return bourseProtocolBuilder;
    }

    public BourseProtocolBuilder header(BourseProtocolHeader header) {
        bourseProtocol.setHeaderLength(codec.write(header).length);
        bourseProtocol.setHeader(header);
        return this;
    }

    public BourseProtocolBuilder body(Object body) {
        byte[] bodyBytes = codec.write(body);
        bourseProtocol.setBodyLength(bodyBytes.length);
        bourseProtocol.setBody(bodyBytes);
        return this;
    }

    public BourseProtocol build() {
        return bourseProtocol;
    }
}