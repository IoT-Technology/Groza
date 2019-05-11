package com.sanshengshui.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAsync
@EnableSwagger2
@SpringBootApplication(scanBasePackages = {"com.sanshengshui.server"})
public class GrozaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrozaServerApplication.class);
    }
}
