package dev.gardeningtool.prism;

import dev.gardeningtool.prism.accountstock.AccountStock;
import dev.gardeningtool.prism.command.CommandManager;
import dev.gardeningtool.prism.event.EventBus;
import dev.gardeningtool.prism.listener.jda.JDAListener;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

@Getter
public class PrismBot {

    private final String TOKEN = "your token here";
    private final String ACTIVITY = "generating accounts with Prism!";

    private AccountStock accountStock;
    private CommandManager commandManager;
    private EventBus eventBus;
    private JDA jda;

    public static void main(String[] args) throws LoginException {
        new PrismBot();
    }

    public PrismBot() throws LoginException {
        this.commandManager = new CommandManager();
        this.eventBus = new EventBus(this);
        this.jda = new JDABuilder().setToken(TOKEN).setActivity(Activity.playing(ACTIVITY)).setStatus(OnlineStatus.DO_NOT_DISTURB).addEventListeners(new JDAListener(eventBus)).build();
        this.accountStock = new AccountStock();
    }
}
