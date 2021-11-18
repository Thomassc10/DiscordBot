package me.thomas.bot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.thomas.bot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class SuggestionCommand extends Command {

    public SuggestionCommand(){
        this.name = "suggest";
        this.aliases = new String[]{"sug"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getAuthor().isBot()) return;
        User author = Bot.getJda().getUserById("340660370656198657");
        TextChannel channel = (TextChannel) event.getChannel();
        channel.purgeMessages(event.getMessage());
        if (event.getArgs() == "") {
            channel.sendMessage("You need to right a message!").queue();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(event.getAuthor().getName() + "'s suggestion");
        builder.setDescription(event.getArgs());
        builder.setThumbnail(event.getAuthor().getAvatarUrl());

        author.openPrivateChannel().flatMap(privateChannel -> privateChannel.sendMessage(builder.build())).queue();
    }
}
