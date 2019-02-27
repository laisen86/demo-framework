package com.example.demoframework.configurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * springMVC配置类
 *
 * @author zhangxueli6
 * @date 2019/2/18
 */
@Configuration
public class SpringMvcConfig {
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        //1. 需要定义一个converter转换消息的对象
        FastJsonHttpMessageConverter fasHttpMessageConverter = new FastJsonHttpMessageConverter();

        //2. 添加fastjson的配置信息，比如:是否需要格式化返回的json的数据
        SerializerFeature[] featrues = new SerializerFeature[]{
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.PrettyFormat
        };

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(featrues);

        //3. 在converter中添加配置信息
        fasHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fasHttpMessageConverter;

        return new HttpMessageConverters(converter);
    }
}
