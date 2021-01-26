package dev.gardeningtool.prism.event.impl;

import dev.gardeningtool.prism.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.entities.*;

@Getter @AllArgsConstructor
public class EventGuildMessageReceived extends Event {

    private User user;
    private Member member;
    private Message message;
    private TextChannel textChannel;

}
