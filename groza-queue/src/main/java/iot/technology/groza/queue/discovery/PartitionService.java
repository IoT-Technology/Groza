package iot.technology.groza.queue.discovery;


import iot.technology.groza.msg.queue.ServiceType;
import iot.technology.groza.msg.queue.TopicPartitionInfo;

import java.util.Set;

/**
 * @author mushuwei
 */
public interface PartitionService {

    TopicPartitionInfo resolve(ServiceType serviceType, String tenantId, String entityId);

    TopicPartitionInfo resolve(ServiceType serviceType, String queueName, String tenantId, String entityId);

    Set<String> getAllServiceIds(ServiceType serviceType);

    TopicPartitionInfo getNotificationsTopic(ServiceType serviceType, String serviceId);
}
