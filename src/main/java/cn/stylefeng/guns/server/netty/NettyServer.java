package cn.stylefeng.guns.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;


/**
 * 服务端
 * 1.创建一个ServerBootstrap的实例引导和绑定服务器。
 * 2.创建并分配一个NioEventLoopGroup实例以进行事件的处理，比如接受连接以及读写数据。
 * 3.指定服务器绑定的本地的InetSocketAddress。
 * 4.使用一个EchoServerHandler的实例初始化每一个新的Channel。
 * 5.调用ServerBootstrap.bind()方法以绑定服务器。
 */
@Component
public class NettyServer implements  Runnable{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    @Override
    public void run() {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        final OutServerHandler outServerHandler = new OutServerHandler();
        ChannelFuture f = null;
        try {
            //ServerBootstrap负责初始化netty服务器，并且开始监听端口的socket请求
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress("192.168.0.110",2000))
                    .option(ChannelOption.SO_SNDBUF, 16*1024)
                    .option(ChannelOption.SO_RCVBUF, 16*1024)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
//                            为监听客户端read/write事件的Channel添加用户自定义的ChannelHandler
                            socketChannel.pipeline().
                                    addLast(new HexDecoder()).
                                    addLast(new StringEncoder()).
                                    addLast(serverHandler).
                                    addLast(outServerHandler);
                        }
                    });

            f = b.bind().sync();
            f.channel().closeFuture().sync();
            log.info("======EchoServer启动成功!!!=========");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != f){
                try {
                    f.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    /**
     * 停止服务
     */
    public void release() {
        log.info("Shutdown Netty Server...");
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        log.info("Shutdown Netty Server Success!");
    }



}
