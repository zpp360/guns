package cn.stylefeng.guns.server.netty;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @ description 解决项目打war包发布在tomcat，端口被占用问题
 * 解决思路：给NettyServer分配一个独立的线程用于加载
 */
@Component
public class NettyServerListener  implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = Logger.getLogger(NettyServerListener.class);

    /**
     * 当一个applicationContext被初始化或被刷新时触发
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            logger.info("NettyServer Start Success");
            //自己的NettyServer
            NettyServer nettyServer = new NettyServer();
            new Thread(nettyServer).start();
        }
    }
}
