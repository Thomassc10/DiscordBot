package me.thomas.bot.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.thomas.bot.musicutils.GuildMusicManager;
import me.thomas.bot.musicutils.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class SkipCommand extends Command {

    public SkipCommand(){
        this.name = "skip";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getAuthor().isBot()) return;
        TextChannel channel = (TextChannel) event.getChannel();

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        AudioPlayer audioPlayer = musicManager.audioPlayer;

        if (audioPlayer.getPlayingTrack() == null) {
            channel.sendMessage("There is no track playing!").queue();
            return;
        }
        musicManager.trackScheduler.nextTrack();
        event.getMessage().addReaction("U+1F44C").queue();
    }
}
