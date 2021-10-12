package me.thomas.bot.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.thomas.bot.musicutils.GuildMusicManager;
import me.thomas.bot.musicutils.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QueueCommand extends Command {

    public QueueCommand(){
        this.name = "queue";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getAuthor().isBot()) return;
        TextChannel channel = (TextChannel) event.getChannel();

        GuildMusicManager guildManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        BlockingQueue<AudioTrack> queue = guildManager.trackScheduler.queue;

        if (queue.isEmpty()){
            channel.sendMessage("The queue is currently empty!").queue();
            return;
        }
        int trackCount = Math.min(queue.size(), 20);
        List<AudioTrack> trackList = new ArrayList<>(queue);
        MessageAction messageAction = channel.sendMessage("**Current Queue**\n");

        for (int i = 0; i < trackCount; i++){
            AudioTrack track = trackList.get(i);
            AudioTrackInfo info = track.getInfo();

            messageAction.append("")
                    .append(i + 1 + ".")
                    .append(" `")
                    .append(info.title)
                    .append("` [`")
                    .append(PlayerManager.formatTime(track.getDuration()))
                    .append("`]\n");
        }

        if (trackList.size() > trackCount){
            messageAction.append("And `").append(String.valueOf(trackList.size() - trackCount))
                    .append("` more...");
        }
        messageAction.queue();
    }

}
