package dev.gardeningtool.prism.command.impl;

import dev.gardeningtool.prism.accountstock.AccountStock;
import dev.gardeningtool.prism.command.Command;
import dev.gardeningtool.prism.user.PrismUserManager;
import dev.gardeningtool.prism.util.MessageUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class GenerateCommand extends Command {

    private final HashMap<Long, Long> LAST_ACCESS_TIME_MAP = new HashMap<>();
    private final Message SYNTAX_MESSAGE = MessageUtil.formattedMessage(MessageUtil.RED, "Syntax error!", null, "Invalid syntax! Try ?generate (account type)");
    private final Message NO_ACCESS_MESSAGE = MessageUtil.formattedMessage(MessageUtil.RED, "No permission!", null, "You don't have permission to generate accounts!");
    private final Message NO_STOCK_MESSAGE = MessageUtil.formattedMessage(MessageUtil.RED, "No stock!", null, "There appears to be no stock! Try again later!");
    private final String ACCOUNT_GENERATED_MESSAGE = "Your generated account is **%s**.\n You have to wait **30 seconds** to generate a new account.\nThe message will also self-delete in 30 seconds.";

    public GenerateCommand() {
        super("generate");
    }

    @Override
    public void onCommand(User executor, String[] args, TextChannel textChannel) {
        long executorId = executor.getIdLong();
        if (LAST_ACCESS_TIME_MAP.containsKey(executorId) && System.currentTimeMillis() - LAST_ACCESS_TIME_MAP.get(executorId) < 3) {
            sendMessage(executor, textChannel, MessageUtil.formattedMessage(MessageUtil.RED, null, null, "You are currently on cooldown!"));
            return;
        }
        if (!PrismUserManager.from(executorId).hasActiveSub()) {
            sendMessage(executor, textChannel, NO_ACCESS_MESSAGE);
            return;
        }
        if (args.length != 2) {
            sendMessage(executor, textChannel, SYNTAX_MESSAGE);
            return;
        }
        try {
            AccountStock.Stock accountType = AccountStock.Stock.valueOf(args[1].toUpperCase());
            try {
                CompletableFuture<Message> completableFuture = executor.openPrivateChannel().complete().sendMessage(MessageUtil.formattedMessage(MessageUtil.GREEN, null, null, String.format(ACCOUNT_GENERATED_MESSAGE, AccountStock.getInstance().getAccount(accountType)))).submit();
                new Thread(() -> {
                    try {
                        Thread.sleep(30000L);
                        completableFuture.get().delete().submit();
                    } catch (InterruptedException | ExecutionException exc) {
                        exc.printStackTrace();
                    }
                }).start();
            } catch (IndexOutOfBoundsException exc) {
                sendMessage(executor, textChannel, NO_STOCK_MESSAGE);
                return;
            }
            if (textChannel != null) { //Only null if the received message is sent in DMs
                sendMessage(executor, textChannel, MessageUtil.formattedMessage(MessageUtil.GREEN, null, null, "Success! The generated account has been sent to your DMs!"));
            }
            LAST_ACCESS_TIME_MAP.put(executorId, System.currentTimeMillis());
        } catch (Exception exc) {
            exc.printStackTrace();
            sendMessage(executor, textChannel, SYNTAX_MESSAGE);
        }
    }
}
