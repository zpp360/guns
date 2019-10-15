package cn.stylefeng.guns.server.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zpp360 on 2019/9/8.
 */
public class ChannelMap {
    /**
     *ChannelGroup是netty提供用于管理客户端与服务器之间建立的通道的channel的，本质是一个高度封装的set集合。
     * 在服务器广播消息时，可以直接通过他的writeAndFlush讲消息发送给集合中的所有channel通道
     * 在查询的时候必须通过channelId查询，所以必须通过map将channelId的字符串和channle保存起来
     */
    private static ChannelGroup globalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static Map<String,ChannelId> channelMap = new ConcurrentHashMap<>();

    public static  void  addChannel(String ip, Channel channel){
        globalGroup.add(channel);
        channelMap.put(ip,channel.id());
    }

    public static void removeChannel(String ip){
        globalGroup.remove(channelMap.get(ip));
        channelMap.remove(ip);
    }

    public static Channel findChannel(String ip){
        return globalGroup.find(channelMap.get(ip));
    }


    public static boolean containsIp(String ip){
        return channelMap.containsKey(ip);
    }

    public static boolean containsChannel(Channel channel){
        return globalGroup.contains(channel);
    }

    public static void sendToAll(String msg){
        globalGroup.writeAndFlush(msg);
    }

}
