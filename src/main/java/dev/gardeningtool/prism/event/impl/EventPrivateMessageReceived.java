package dev.gardeningtool.prism.event.impl;

import dev.gardeningtool.prism.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;

@Getter @AllArgsConstructor
public class EventPrivateMessageReceived extends Event {

    private User author;
    private Message message;
    private PrivateChannel privateChannel;

}
