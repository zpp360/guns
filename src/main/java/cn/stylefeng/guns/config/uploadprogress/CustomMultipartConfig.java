package cn.stylefeng.guns.config.uploadprogress;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;

@Configuration
public class CustomMultipartConfig {

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver(){
        return new CustomMultipartResolver();
    }

}
