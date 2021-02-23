package com.qingcha.bourse.protocol;

import com.qingcha.bourse.commons.codec.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 *
 * @author qiqiang
 */
public class ProtocolToByteEncoder extends MessageToByteEncoder<BourseProtocol> {
    private final Codec codec;

    public ProtocolToByteEncoder(Codec codec) {
        this.codec = codec;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, BourseProtocol rpcRequest, ByteBuf byteBuf) throws Exception {
        BourseProtocolHeader header = rpcRequest.getHeader();
        // 将 header 转 byte 数组
        byte[] headerData = codec.write(header);
        byte[] body = rpcRequest.getBody();
        // 写入开始标志
        byteBuf.writeInt(BourseProtocolConst.HEAD_START);
        // 写入头信息
        byteBuf.writeInt(headerData.length);
        byteBuf.writeBytes(headerData);
        // 写入 body 信息
        if (body == null) {
            byteBuf.writeInt(0);
        } else {
            byteBuf.writeInt(body.length);
            byteBuf.writeBytes(body);
        }
    }
}