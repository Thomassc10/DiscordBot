package me.thomas.bot.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jlyrics.LyricsClient;
import me.thomas.bot.musicutils.GuildMusicManager;
import me.thomas.bot.musicutils.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class LyricsCommand extends Command {

    public LyricsCommand(){
        this.name = "lyrics";
    }

    @Override
    protected void execute(CommandEvent event) {
        TextChannel channel = (TextChannel) event.getChannel();
        GuildMusicManager guildManager = PlayerManager.getInstance().getMusicManager(event.getGuild());

        if (guildManager.audioPlayer.getPlayingTrack() == null){
            channel.sendMessage("There is no song playing!").queue();
            return;
        }

        String title = guildManager.audioPlayer.getPlayingTrack().getInfo().title;
        LyricsClient client = new LyricsClient();
        client.getLyrics(title).thenAccept(lyrics -> {
            if (lyrics == null) {
                channel.sendMessage("Error while trying to load the lyrics " + lyrics.getURL()).queue();
                return;
            }

            EmbedBuilder builder = new EmbedBuilder()
                    .setAuthor(lyrics.getAuthor())
                    .setColor(10181046)
                    .setTitle(lyrics.getTitle());
            //builder.setDescription(lyrics.getContent());

            if(lyrics.getContent().length() > 15000) {
                channel.sendMessage("Lyrics for `" + title + "` found but likely not correct: " + lyrics.getURL()).queue();
            }
            else if(lyrics.getContent().length() > 2000) {
                String content = lyrics.getContent().trim();
                while(content.length() > 2000) {
                    int index = content.lastIndexOf("\n\n", 2000);
                    if(index == -1)
                        index = content.lastIndexOf("\n", 2000);
                    if(index == -1)
                        index = content.lastIndexOf(" ", 2000);
                    if(index == -1)
                        index = 2000;
                    channel.sendMessage(builder.setDescription(content.substring(0, index).trim()).build()).queue();
                    content = content.substring(index).trim();
                    builder.setAuthor(null).setTitle(null, null);
                }
                channel.sendMessage(builder.setDescription(content).build()).queue();
                event.getMessage().addReaction("U+1F44C").queue();
            }
            else {
                channel.sendMessage(builder.build()).queue();
                event.getMessage().addReaction("U+1F44C").queue();
            }
        });
    }
}
