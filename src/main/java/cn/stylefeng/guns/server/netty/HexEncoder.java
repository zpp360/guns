//package cn.stylefeng.guns.server.netty;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.ByteBufUtil;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToByteEncoder;
//
///**
// * Created by zpp360 on 2019/9/16.
// * 编码器
// */
//public class HexEncoder extends MessageToByteEncoder {
//
//    @Override
//    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf out) throws Exception {
//        out.writeBytes(ByteBufUtil.decodeHexDump(o.toString()));
//    }
//}
