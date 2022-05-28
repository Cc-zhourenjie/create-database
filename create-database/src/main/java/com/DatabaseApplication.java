package com;

import com.cc.mybaits.utils.MapperUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
//@MapperScan
public class DatabaseApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(DatabaseApplication.class, args);
        MapperUtils.setApplicationContext(applicationContext);
        System.out.println("==================================项目已经启动==================================");

    }
}
