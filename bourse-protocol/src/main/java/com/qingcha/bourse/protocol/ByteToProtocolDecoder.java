package com.qingcha.bourse.protocol;

import com.qingcha.bourse.commons.codec.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码器
 *
 * @author qiqiang
 */
public class ByteToProtocolDecoder extends ByteToMessageDecoder {
    private final Codec codec;

    public ByteToProtocolDecoder(Codec codec) {
        this.codec = codec;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        try {
            int begin;
            // 在循环中找到协议开始的位置
            while (true) {
                // 本次协议包开始的位置
                begin = byteBuf.readerIndex();
                // 标记本次协议包开始的位置
                byteBuf.markReaderIndex();
                if (byteBuf.readInt() == BourseProtocolConst.HEAD_START) {
                    break;
                }
                // 没有读到 HEAD_START，那么就读取下一个字节
                byteBuf.resetReaderIndex();
                byteBuf.readByte();
            }
            // 协议包头长度
            int headerLength = byteBuf.readInt();
            // 协议包头数据还未到齐，回到协议开始的位置，等待数据到齐
            if (byteBuf.readableBytes() < headerLength) {
                byteBuf.readerIndex(begin);
                return;
            }
            // 读取协议包头数据
            byte[] header = new byte[headerLength];
            byteBuf.readBytes(header);
            // 协议内容长度
            int bodyLength = byteBuf.readInt();
            // 协议包内容数据还未到齐，回到协议开始的位置，等待数据到齐
            if (byteBuf.readableBytes() < bodyLength) {
                byteBuf.readerIndex(begin);
                return;
            }
            // 读取协议包内容数据
            byte[] body = new byte[bodyLength];
            byteBuf.readBytes(body);
            // 封装协议
            BourseProtocol rpcProtocol = new BourseProtocol();
            rpcProtocol.setHeaderLength(headerLength);
            // 将 byte 数组转成 header
            BourseProtocolHeader rpcProtocolHeader = codec.read(header, BourseProtocolHeader.class);
            rpcProtocol.setHeader(rpcProtocolHeader);
            rpcProtocol.setBodyLength(bodyLength);
            rpcProtocol.setBody(body);
            out.add(rpcProtocol);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}