package me.thomas.bot.musicutils;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class PlayerManager {

    private static PlayerManager instance;
    private Map<Long, GuildMusicManager> musicManagers;
    public AudioPlayerManager audioPlayerManager;
    public Map<String, Long> messageMap;
    public Map<String, Long> channelMap;
    public PlayerManager(){
        instance = this;
        musicManagers = new HashMap<>();
        messageMap = new HashMap<>();
        channelMap = new HashMap<>();
        audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
    }

    public static PlayerManager getInstance(){
        if (instance == null)
            instance = new PlayerManager();
        return instance;
    }

    public static String formatTime(long timeInMillis) {
        long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public GuildMusicManager getMusicManager(Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
            return guildMusicManager;
        });
    }

    public void putMapValues(TextChannel channel){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                messageMap.put("playing", channel.getLatestMessageIdLong());
                channelMap.put("playing", channel.getIdLong());
            }
        };
        timer.schedule(task, 500);
    }

    public void sendQueuedEmbed(TextChannel channel, AudioTrack audioTrack){
        EmbedBuilder builder = new EmbedBuilder();
        String uri = audioTrack.getInfo().uri;
        builder.setColor(0xf55742);
        builder.setDescription("Queued [" + audioTrack.getInfo().title + "](" + uri + ")");
        channel.sendMessage(builder.build()).queue();
        builder.clear();
    }

    public void sendPlayingEmbed(TextChannel channel, AudioTrack audioTrack){
        EmbedBuilder builder = new EmbedBuilder();
        String uri = audioTrack.getInfo().uri;
        builder.setColor(0xf55742);
        builder.setTitle("Now playing");
        builder.setDescription("[" + audioTrack.getInfo().title + "](" + uri + ")");
        channel.sendMessage(builder.build()).queue();
        builder.clear();
        putMapValues(channel);
    }

    public void loadAndPlay(TextChannel channel, String url){
        GuildMusicManager musicManager = getMusicManager(channel.getGuild());

        audioPlayerManager.loadItemOrdered(musicManager, url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                if (musicManager.audioPlayer.getPlayingTrack() == null) {
                    sendPlayingEmbed(channel, audioTrack);
                }else sendQueuedEmbed(channel, audioTrack);
                musicManager.trackScheduler.queue(audioTrack);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                List<AudioTrack> trackList = audioPlaylist.getTracks();
                AudioTrack audioTrack;
                if (audioPlaylist.isSearchResult()) {
                    audioTrack = trackList.get(0);
                    if (musicManager.audioPlayer.getPlayingTrack() == null) {
                        sendPlayingEmbed(channel, audioTrack);
                    }else sendQueuedEmbed(channel, audioTrack);
                    musicManager.trackScheduler.queue(audioTrack);
                }else{
                    int trackCount = Math.min(trackList.size(), 20);
                    MessageAction messageAction = channel.sendMessage("**Added to queue**\n");

                    for (int i = 0; i < trackCount; i++){
                        AudioTrack track = trackList.get(i);
                        AudioTrackInfo info = track.getInfo();

                        messageAction.append("")
                                .append(i + 1 + ".")
                                .append(" `")
                                .append(info.title)
                                .append("` [`")
                                .append(formatTime(track.getDuration()))
                                .append("`]\n");
                    }
                    for (int i = 0; i < trackList.size(); i++){
                        audioTrack = trackList.get(i);
                        musicManager.trackScheduler.queue(audioTrack);
                    }
                    if (trackList.size() > trackCount){
                        messageAction.append("And `").append(String.valueOf(trackList.size() - trackCount))
                                .append("` more...");
                    }
                    messageAction.queue();
                }
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Song not found!").queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                channel.sendMessage("Couldn't load the song!").queue();
            }
        });
    }
}
