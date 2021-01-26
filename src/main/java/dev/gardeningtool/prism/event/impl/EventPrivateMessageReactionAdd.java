package dev.gardeningtool.prism.event.impl;

import dev.gardeningtool.prism.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;

@Getter @AllArgsConstructor
public class EventPrivateMessageReactionAdd extends Event {

    private User user;
    private PrivateChannel privateChannel;
    private MessageReaction messageReaction;
    private long messageId;

}
