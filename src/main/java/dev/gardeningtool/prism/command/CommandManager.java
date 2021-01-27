package dev.gardeningtool.prism.command;

import dev.gardeningtool.prism.command.impl.AddTimeCommand;
import dev.gardeningtool.prism.command.impl.GenerateCommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.List;

public class CommandManager {

    private List<Command> commands;

    public CommandManager() {
        commands = Arrays.asList(
                new AddTimeCommand(),
                new GenerateCommand()
        );
    }

    public void onCommand(String commandName, User user, String[] args, TextChannel textChannel) {
        commands.stream().filter(command -> command.getName().equalsIgnoreCase(commandName)).forEach(command -> command.onCommand(user, args, textChannel));
    }

}
