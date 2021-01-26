package dev.gardeningtool.prism.event.impl;

import dev.gardeningtool.prism.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class EventGatewayPing extends Event {

    private long oldPing, newPing;

}
