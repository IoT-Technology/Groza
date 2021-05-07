package iot.technology.groza.queue.memory;

import iot.technology.groza.queue.GaQueueMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author mushuwei
 * @date 2021/4/28 11:29 上午
 */
@Slf4j
public final class InMemoryStorage {
    private static InMemoryStorage instance;
    private final ConcurrentHashMap<String, BlockingQueue<GaQueueMsg>> storage;

    private InMemoryStorage() {
        storage = new ConcurrentHashMap<>();
    }

    public void printStats() {
        storage.forEach((topic, queue) -> {
            if (queue.size() > 0) {
                log.debug("[{}] Queue Size [{}]", topic, queue.size());
            }
        });
    }

    public static InMemoryStorage getInstance() {
        if (instance == null) {
            synchronized (InMemoryStorage.class) {
                if (instance == null) {
                    instance = new InMemoryStorage();
                }
            }
        }
        return instance;
    }

    public boolean put(String topic, GaQueueMsg msg) {
        return storage.computeIfAbsent(topic, (t) -> new LinkedBlockingQueue<>()).add(msg);
    }

    public <T extends GaQueueMsg> List<T> get(String topic) throws InterruptedException {
        if (storage.containsKey(topic)) {
            List<T> entities;
            @SuppressWarnings("unchecked")
            T first = (T) storage.get(topic).poll();
            if (first != null) {
                entities = new ArrayList<>();
                entities.add(first);
                List<GaQueueMsg> otherList = new ArrayList<>();
                storage.get(topic).drainTo(otherList, 999);
                for (GaQueueMsg other : otherList) {
                    @SuppressWarnings("unchecked")
                    T entity = (T) other;
                    entities.add(entity);
                }
            } else {
                entities = Collections.emptyList();
            }
            return entities;
        }
        return Collections.emptyList();
    }

    /**
     * Used primarily for testing.
     */
    public void cleanup() {
        storage.clear();
    }
}
