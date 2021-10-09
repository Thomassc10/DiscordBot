package commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand extends Command {

    public LeaveCommand(){
        this.name = "leave";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getAuthor().isBot()) return;
        VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();
        TextChannel channel = (TextChannel) event.getChannel();

        if (voiceChannel == null){
            channel.sendMessage("You are not connected to the voice channel!").queue();
            channel.purgeMessages(event.getMessage());
            return;
        }
        if (!audioManager.isConnected() && audioManager.getConnectedChannel() != voiceChannel){
            channel.sendMessage("I'm not connect to this voice channel!").queue();
            channel.purgeMessages(event.getMessage());
            return;
        }

        audioManager.closeAudioConnection();
        channel.purgeMessages(event.getMessage());
    }
}
