package me.thomas.bot.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class BotJoinEvent extends ListenerAdapter {

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        TextChannel channel = event.getGuild().getDefaultChannel();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Aleijados Bot");
        builder.setDescription("Thanks for adding me to your server!");
        builder.addField("Commands", "help, clean, suggest", false);
        builder.addField("Music Commands", "join/leave, play, playnext, stop, skip, queue, song,\nshuffle, clear", false);
        builder.setColor(10181046);
        builder.setFooter("Created by Memé", "https://cdn.discordapp.com/attachments/365949879723491328/829724901119361024/Profile_Picture.png");
        channel.sendMessage(builder.build()).queue();
        builder.clear();
    }
}
