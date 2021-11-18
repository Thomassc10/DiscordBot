package me.thomas.bot.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.thomas.bot.musicutils.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.MalformedURLException;
import java.net.URL;

public class PlayCommand extends Command {

    public PlayCommand(){
        this.name = "play";
        this.aliases = new String[]{"p"};
        this.help = "Selects a song to play.";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getAuthor().isBot()) return;
        String[] message = event.getMessage().getContentRaw().split(" ");
        VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();
        TextChannel channel = (TextChannel) event.getChannel();

        if (message.length == 1){
            channel.sendMessage("Usage: -play <music-name>").queue();
            channel.purgeMessages(event.getMessage());
            return;
        }
        if (voiceChannel == null){
            channel.sendMessage("You need to be connected to a voice channel!").queue();
            channel.purgeMessages(event.getMessage());
            return;
        }
        if (!audioManager.isSelfDeafened())
            audioManager.setSelfDeafened(true);
        if (!audioManager.isConnected())
            audioManager.openAudioConnection(voiceChannel);

        String link = String.join(" ", event.getArgs());

        if (!isUrl(link)){
            link = "ytsearch:" + link;
        }

        PlayerManager.getInstance().loadAndPlay(channel, link, false);
    }

    private boolean isUrl(String url){
        try{
            new URL(url);
            return true;
        }catch(MalformedURLException e){
            return false;
        }
    }
}
