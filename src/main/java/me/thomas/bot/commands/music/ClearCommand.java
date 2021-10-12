package me.thomas.bot.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.thomas.bot.musicutils.GuildMusicManager;
import me.thomas.bot.musicutils.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class ClearCommand extends Command {

    public ClearCommand(){
        this.name = "clear";
        this.aliases = new String[]{"cl"};
    }

    @Override
    protected void execute(CommandEvent event) {
        TextChannel channel = (TextChannel) event.getChannel();
        GuildMusicManager guildManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        if (guildManager.trackScheduler.queue.isEmpty()){
            channel.sendMessage("The queue is currently empty!").queue();
            return;
        }
        guildManager.trackScheduler.queue.clear();
        event.getMessage().addReaction("U+1F44C").queue();
    }
}
