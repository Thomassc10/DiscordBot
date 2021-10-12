package me.thomas.bot.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.thomas.bot.musicutils.GuildMusicManager;
import me.thomas.bot.musicutils.PlayerManager;

public class SongCommand extends Command {

    public SongCommand(){
        this.name = "song";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getAuthor().isBot()) return;
        GuildMusicManager guildManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        String title = guildManager.audioPlayer.getPlayingTrack().getInfo().title;
        long duration = guildManager.audioPlayer.getPlayingTrack().getDuration();
        event.getChannel().sendMessage(title + " - " + PlayerManager.formatTime(duration)).queue();
    }
}
