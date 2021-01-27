package dev.gardeningtool.prism.command;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

//TODO: Add command aliases
public abstract class Command {

    @Getter private String name;

    public Command(String name) {
        this.name = name;
    }

    public abstract void onCommand(User executor, String[] args, TextChannel textChannel);

    protected final void sendMessage(User executor, TextChannel textChannel, Message message) {
        if (textChannel != null) {
            textChannel.sendMessage(message).submit();
        } else {
            executor.openPrivateChannel().complete().sendMessage(message).submit();
        }
    }
}
