package dev.gardeningtool.prism.event.impl;

import dev.gardeningtool.prism.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class EventBotStartup extends Event {

    private long start, end;

    public long getTimeElapsed() {
        return end - start;
    }
}
