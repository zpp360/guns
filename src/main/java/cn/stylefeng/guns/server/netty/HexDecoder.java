package cn.stylefeng.guns.server.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by zpp360 on 2019/9/16.
 * 解码器
 */
public class HexDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes()>0){
            String hexStr = ByteBufUtil.hexDump(byteBuf);
            list.add(hexStr);
            //避免错误did not read anything but decoded a message
            byteBuf.skipBytes(byteBuf.readableBytes());
        }
    }
}
