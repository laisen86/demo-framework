package com.example.demoframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//@MapperScan(basePackages = "com.example.demoframework.dao")
@MapperScan(basePackages ="com.example.demoframework.dao" )
public class DemoFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoFrameworkApplication.class, args);
    }

}
