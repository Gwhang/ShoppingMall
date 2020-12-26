package com.gwh.sell.logger;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 日志测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

    // slf4j包对应方法 后面参数写类名，参数写什么，日志输出什么
    private final  Logger logger = LoggerFactory.getLogger(LoggerTest.class);


    @Test
    public void test1(){
        logger.debug("debug");
        logger.info("info");//系统默认日志级别是 info info以上的可以输出出来
        logger.error("error");
        logger.info("名字 {} 年龄 {}","张三","25");

    }

}
