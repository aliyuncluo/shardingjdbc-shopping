package com.cluo.shopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@MapperScan(basePackages = {"com.cluo.shopping.dao"})
@SpringBootApplication
public class ShardingjdbcShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingjdbcShoppingApplication.class, args);
    }

}
