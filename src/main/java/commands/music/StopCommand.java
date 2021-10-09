package commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import musicutils.GuildMusicManager;
import musicutils.PlayerManager;

public class StopCommand extends Command {

    public StopCommand(){
        this.name = "stop";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getAuthor().isBot()) return;
        GuildMusicManager guildManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        if (!guildManager.audioPlayer.isPaused()){
            guildManager.audioPlayer.setPaused(true);
            event.getChannel().sendMessage("Paused the queue!").queue();
        }else {
            guildManager.audioPlayer.setPaused(false);
            event.getChannel().sendMessage("Continued the queue!").queue();
        }
    }
}
