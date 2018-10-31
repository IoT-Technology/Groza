package com.sanshengshui.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.sanshengshui.server"})
public class GrozaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrozaServerApplication.class);
    }
}
