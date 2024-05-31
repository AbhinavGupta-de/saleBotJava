package org.abhinavgpt.salebotjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestSaleBotJavaApplication {

    public static void main(String[] args) {
        SpringApplication.from(SaleBotJavaApplication::main).with(TestSaleBotJavaApplication.class).run(args);
    }

}
