package me.thomas.bot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;

public class ClearMessages extends Command {

    public ClearMessages(){
        this.name = "clean";
    }

    @Override
    protected void execute(CommandEvent event) {
        List<Message> allMessages = event.getChannel().getHistory().retrievePast(100).complete();
        for (Message m : allMessages){
            if (m.getAuthor().isBot())
                event.getChannel().purgeMessages(m);
            event.getChannel().purgeMessages(event.getMessage());
        }
    }
}
