package dev.gardeningtool.prism.event.impl;

import dev.gardeningtool.prism.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.entities.User;

@Getter @AllArgsConstructor
public class EventUserUpdateName extends Event {

    private User user;
    private String oldName, newName;

}
