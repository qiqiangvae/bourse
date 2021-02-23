package com.qingcha.bourse.server.event;

import com.qingcha.bourse.commons.event.BourseEvent;
import com.qingcha.bourse.server.BourseEventContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author qiqiang
 */
public class BaseBourseEventProcessor implements BourseEventProcessor<BourseEvent> {
    private static final Map<Class<? extends BourseEvent>, BourseEventProcessor> PROCESSORS;
    private final ExecutorService executorService;

    public BaseBourseEventProcessor() {
        executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()
                , 20, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "BourseEventProcessor-thread");
            }
        });
    }

    static {
        PROCESSORS = new HashMap<>();
    }

    public void registerProcessor(Class<? extends BourseEvent> event, BourseEventProcessor processor) {
        PROCESSORS.put(event, processor);
    }


    @Override
    public void process(BourseEvent bourseEvent, BourseEventContext context) {
        if (bourseEvent == null) {
            return;
        }
        for (Map.Entry<Class<? extends BourseEvent>, BourseEventProcessor> entry : PROCESSORS.entrySet()) {
            if (bourseEvent.getClass().equals(entry.getKey())) {
                entry.getValue().process(bourseEvent, context);
                break;
            }
        }
    }

    public ExecutorService executor() {
        return executorService;
    }
}