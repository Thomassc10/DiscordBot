package me.thomas.bot.commands;


import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.Button;

public class HelpCommand extends Command {

    public HelpCommand(){
        this.name = "help";
        this.help = "Gives information about the bot.";
    }

    @Override
    protected void execute(CommandEvent event) {
        TextChannel channel = (TextChannel) event.getChannel();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Aleijados Bot");
        builder.setDescription("Information about the bot");
        builder.addField("Commands", "help, clean, suggest", false);
        builder.addField("Music Commands", "join/leave, play, playnext, stop, skip, queue, song,\nshuffle, clear, lyrics", false);
        builder.setColor(10181046);
        builder.setFooter("Created by Memé", "https://cdn.discordapp.com/attachments/365949879723491328/829724901119361024/Profile_Picture.png");
        channel.sendMessage(builder.build()).setActionRow(Button.primary("ping", "Ping"),
                Button.link("https://github.com/Thomassc10/DiscordBot", "GitHub"),
                Button.secondary("clear", Emoji.fromUnicode("U+274C"))).queue();
    }
}
