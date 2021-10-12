package me.thomas.bot.musicutils;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import me.thomas.bot.Bot;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class TrackScheduler extends AudioEventAdapter {

    private AudioPlayer player;
    public BlockingQueue<AudioTrack> queue;
    public TrackScheduler(AudioPlayer player){
        this.player = player;
        this.queue = new LinkedBlockingDeque<>();
    }

    public void queue(AudioTrack track){
        if (!player.startTrack(track, true)){
            queue.offer(track);
        }
    }

    public void nextTrack(){
        this.player.startTrack(this.queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext){
            nextTrack();
        }
        PlayerManager playerManager = PlayerManager.getInstance();
        Long channelId = playerManager.channelMap.get("playing");
        Long messageId = playerManager.messageMap.get("playing");
        TextChannel channel = Bot.getJda().getTextChannelById(channelId);
        channel.purgeMessagesById(messageId);
        playerManager.sendPlayingEmbed(channel, player.getPlayingTrack());
    }
}
