package com.sanshengshui.rule.engine.producers;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

/**
 * @author james mu
 * @date 2019/5/11 19:08
 */
public class producers {
    public static void PulsarClient() throws PulsarClientException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .build();
        Producer<byte[]> producer = client.newProducer()
                .topic("my-topic")
                .create();
        producer.send("My message".getBytes());
        client.close();
        producer.close();
    }
}
