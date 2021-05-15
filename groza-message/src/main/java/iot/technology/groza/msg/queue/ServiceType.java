package iot.technology.groza.msg.queue;

/**
 * @author mushuwei
 */
public enum ServiceType {
    GA_CORE, GA_RULE_ENGINE, GA_TRANSPORT;

    public static ServiceType of(String serviceType) {
        return ServiceType.valueOf(serviceType.replace("-", "_").toUpperCase());
    }
}
