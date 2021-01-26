package dev.gardeningtool.prism.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;

public class MessageUtil {

    public static final Color RED = new Color(209, 22, 8);
    public static final Color GREEN = new Color(3, 145, 34);
    public static final Color BLUE = new Color(3, 9, 133);

    public static final Message INSUFFICIENT_PERMISSION = formattedMessage(RED, "Error", null, "You don't have permission to do this!");

    public static Message message(String msg) {
        return new MessageBuilder().setContent(msg).build();
    }

    public static Message formattedMessage(Color color, String title, String footer, String desc) {
        return new MessageBuilder().setEmbed(new EmbedBuilder().setColor(color).setFooter(footer).setDescription(desc).setTitle(title, null).build()).build();
    }

}
