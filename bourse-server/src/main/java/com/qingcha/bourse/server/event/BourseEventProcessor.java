package com.qingcha.bourse.server.event;

import com.qingcha.bourse.commons.event.BourseEvent;
import com.qingcha.bourse.server.BourseEventContext;

/**
 * @author qiqiang
 */
public interface BourseEventProcessor<T extends BourseEvent> {
    default void process(T event, BourseEventContext executor) {
    }
}