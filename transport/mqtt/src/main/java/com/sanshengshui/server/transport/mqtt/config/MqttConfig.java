package com.sanshengshui.server.transport.mqtt.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author james mu
 * @date 18-12-7 下午4:29
 */
@Data
@Configuration
@ConditionalOnProperty(prefix = "mqtt", value = "enabled", matchIfMissing = false)
public class MqttConfig {

    @Value("${mqtt.bind_address}")
    private String host;
    @Value("${mqtt.bind_port}")
    private Integer port;
    @Value("${mqtt.adaptor}")
    private String adaptorName;

    @Value("${mqtt.netty.leak_detector_level}")
    private String leakDetectorLevel;
    @Value("${mqtt.netty.boss_group_thread_count}")
    private Integer bossGroupThreadCount;
    @Value("${mqtt.netty.worker_group_thread_count}")
    private Integer workerGroupThreadCount;
    @Value("${mqtt.netty.max_payload_size}")
    private Integer maxPayloadSize;


    @Bean(name = "host")
    public String host() {
        return host;
    }

    @Bean(name="port")
    public Integer port() {
        return port;
    }

    @Bean(name="leakDetectorLevel")
    public String leakDetectorLevel() {
        return leakDetectorLevel;
    }

    @Bean(name="bossGroupThreadCount")
    public Integer bossGroupThreadCount() {
        return bossGroupThreadCount;
    }

    @Bean(name="workerGroupThreadCount")
    public Integer workerGroupThreadCount() {
        return workerGroupThreadCount;
    }

    @Bean(name="maxPayloadSize")
    public Integer maxPayloadSize() {
        return maxPayloadSize;
    }
}
