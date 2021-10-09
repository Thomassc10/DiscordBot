package musicutils;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

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
    }
}
