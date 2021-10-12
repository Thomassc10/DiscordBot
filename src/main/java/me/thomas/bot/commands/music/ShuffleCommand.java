package me.thomas.bot.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.thomas.bot.musicutils.GuildMusicManager;
import me.thomas.bot.musicutils.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ShuffleCommand extends Command {

    public ShuffleCommand(){
        this.name = "shuffle";
    }

    @Override
    protected void execute(CommandEvent event) {
        TextChannel channel = (TextChannel) event.getChannel();
        GuildMusicManager guildManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        BlockingQueue<AudioTrack> queue = guildManager.trackScheduler.queue;
        List<AudioTrack> trackList = new ArrayList<>(queue);
        if (trackList.isEmpty() || trackList.size() < 3){
            channel.sendMessage("The queue is currently empty or too short to shuffle!").queue();
            return;
        }
        Collections.shuffle(trackList);
        queue.clear();
        for (AudioTrack audioTrack : trackList) {
            queue.offer(audioTrack);
        }
        event.getMessage().addReaction("U+1F44C").queue();
    }
}
