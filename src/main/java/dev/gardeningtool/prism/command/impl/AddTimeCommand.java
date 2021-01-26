package dev.gardeningtool.prism.command.impl;

import dev.gardeningtool.prism.util.MessageUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class AddTimeCommand extends Command {

    private final HashMap<String, Long> TIME_MAP = new HashMap<>();
    private final Message ARGUMENTS_MESSAGE = MessageUtil.formattedMessage
            (MessageUtil.RED, "Invalid arguments", null, "Invalid arguments! Try ?add (user) (time)");

    private final Long[] ADMIN_USER_IDS = {
            726179896333697184L,
            785903886573240350L
    };

    public AddTimeCommand() {
        super("add");
        TIME_MAP.put("H", 3600000L);
        TIME_MAP.put("D", 3600000L * 24L);
        TIME_MAP.put("W", 3600000L * 24L * 7);
        TIME_MAP.put("M", 3600000L * 30);
    }

    @Override
    public void onCommand(User executor, String[] args, TextChannel textChannel) {
        if (!(Arrays.asList(ADMIN_USER_IDS).contains(executor.getIdLong()))) {
            sendMessage(executor, textChannel, MessageUtil.INSUFFICIENT_PERMISSION);
            return;
        }
        if (args.length != 2) {
            sendMessage(executor, textChannel, ARGUMENTS_MESSAGE);
            return;
        }
        long targetId;
        try {
            if (args[1].contains("<@")) {
                targetId = Long.parseLong(args[1].replace("<@", "").replace("!", "").replace(">", ""));
            } else {
                targetId = Long.parseLong(args[1]);
            }
        } catch (NumberFormatException exc) {
            sendMessage(executor, textChannel, ARGUMENTS_MESSAGE);
        }
    }
}
