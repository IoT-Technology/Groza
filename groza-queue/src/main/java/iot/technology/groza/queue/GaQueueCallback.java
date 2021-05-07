package iot.technology.groza.queue;

/**
 * @author mushuwei
 * @date 2021/5/7 11:39 下午
 */
public interface GaQueueCallback {

    void onSuccess(GaQueueMsgMetadata metadata);

    void onFailure(Throwable t);
}
