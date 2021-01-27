package dev.gardeningtool.prism.command.impl;

import dev.gardeningtool.prism.command.Command;
import dev.gardeningtool.prism.user.PrismUser;
import dev.gardeningtool.prism.user.PrismUserManager;
import dev.gardeningtool.prism.util.MessageUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.HashMap;

//TODO: Find a cleaner way to do this
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
        if (args.length != 3) {
            sendMessage(executor, textChannel, ARGUMENTS_MESSAGE);
            return;
        }
        long targetId = 0;
        try {
            if (args[1].contains("<@")) {
                targetId = Long.parseLong(args[1].replace("<@", "").replace("!", "").replace(">", ""));
            } else {
                targetId = Long.parseLong(args[1]);
            }
        } catch (NumberFormatException exc) {
            sendMessage(executor, textChannel, ARGUMENTS_MESSAGE);
        }
        PrismUser user = PrismUserManager.from(targetId);
        try {
            String last = Character.toString(args[2].charAt(args[2].length() - 1));
            long mappedTime = TIME_MAP.get(last.toUpperCase());
            int multiplier = Integer.parseInt(args[2].replace(last, ""));
            long time = mappedTime * multiplier;
            user.setEndTime(user.getEndTime() == 0 ? System.currentTimeMillis() + time : user.getEndTime() + time);
            if (user.getStartTime() == 0) {
                user.setStartTime(System.currentTimeMillis());
            }
            user.saveFile(targetId);
            sendMessage(executor, textChannel, MessageUtil.formattedMessage(MessageUtil.GREEN, "Success!", null, String.format("Successfully added a %d%s subscription to <@%s>!", multiplier, last, targetId)));
        } catch (ArrayIndexOutOfBoundsException | NullPointerException exc) {
            sendMessage(executor, textChannel, ARGUMENTS_MESSAGE);
        }
    }
}
