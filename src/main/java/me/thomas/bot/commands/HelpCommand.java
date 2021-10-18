package me.thomas.bot.commands;


import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.thomas.bot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

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
        builder.addField("Prefix: -", "", false);
        builder.addField("Commands", "**help**\n**clean**\n**join/leave**\n**play**\n**stop**\n**skip**\n**queue**\n**song**\n**shuffle**\n**clear**", false);
        builder.setColor(0xf55742);
        builder.setFooter("Created by Memé", "https://cdn.discordapp.com/attachments/365949879723491328/829724901119361024/Profile_Picture.png");
        channel.sendMessage(builder.build()).queue();
        builder.clear();
    }

}
