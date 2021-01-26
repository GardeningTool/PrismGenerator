package dev.gardeningtool.prism.listener.jda;

import dev.gardeningtool.prism.event.EventBus;
import dev.gardeningtool.prism.event.impl.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.events.GatewayPingEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateVanityCodeEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateDiscriminatorEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor @Getter
public class JDAListener extends ListenerAdapter {

    private EventBus eventBus;

    @Override
    public void onGatewayPing(@NotNull GatewayPingEvent event) {
        eventBus.post(new EventGatewayPing(event.getOldPing(), event.getNewPing()));
    }

    @Override
    public void onUserUpdateName(@NotNull UserUpdateNameEvent event) {
        eventBus.post(new EventUserUpdateName(event.getUser(), event.getOldName(), event.getNewName()));
    }

    @Override
    public void onUserUpdateDiscriminator(@NotNull UserUpdateDiscriminatorEvent event) {
        eventBus.post(new EventUserUpdateDiscriminator(event.getUser(), event.getOldDiscriminator(), event.getNewDiscriminator()));
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        eventBus.post(new EventGuildMessageReceived(event.getAuthor(), event.getMember(), event.getMessage(), event.getChannel()));
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        eventBus.post(new EventGuildMessageReactionAdd(event.getUser(), event.getMember(), event.getReaction(), event.getMessageIdLong(), event.getChannel()));
    }

    @Override
    public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {
        eventBus.post(new EventGuildMessageReactionRemove(event.getUser(), event.getMember(), event.getReaction(), event.getMessageIdLong(), event.getChannel()));
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        eventBus.post(new EventPrivateMessageReceived(event.getAuthor(), event.getMessage(), event.getChannel()));
    }

    @Override
    public void onPrivateMessageReactionAdd(@NotNull PrivateMessageReactionAddEvent event) {
        eventBus.post(new EventPrivateMessageReactionAdd(event.getUser(), event.getChannel(), event.getReaction(), event.getMessageIdLong()));
    }

    @Override
    public void onGuildUpdateVanityCode(@NotNull GuildUpdateVanityCodeEvent event) {
        eventBus.post(new EventUpdateVanityInvite(event.getOldVanityCode(), event.getNewVanityCode()));
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        eventBus.post(new EventGuildMemberJoin(event.getUser()));
    }

    @Override
    public void onGuildMemberLeave(@NotNull GuildMemberLeaveEvent event) {
        eventBus.post(new EventGuildMemberLeave(event.getUser()));
    }


}
