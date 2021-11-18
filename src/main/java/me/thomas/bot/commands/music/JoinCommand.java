package me.thomas.bot.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand extends Command {

    public JoinCommand(){
        this.name = "join";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getAuthor().isBot()) return;
        VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();
        TextChannel channel = (TextChannel) event.getChannel();

        if (audioManager.isConnected()){
            channel.sendMessage("I'm already connected to a voice channel!").queue();
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

        event.getMessage().addReaction("U+1F44C").queue();
    }
}
