package cn.stylefeng.guns.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zpp360 on 2019/10/12.
 * netty配置文件
 */
@Configuration
@ConfigurationProperties(prefix = NettyProperties.NETTYCONF_PREFIX)
public class NettyProperties {

    public static final String NETTYCONF_PREFIX = "netty";

    private String url;

    private String port;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
