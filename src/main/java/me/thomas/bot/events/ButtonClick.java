package me.thomas.bot.events;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ButtonClick extends ListenerAdapter {

    @Override
    public void onButtonClick(ButtonClickEvent event) {
        if (event.getComponentId().equals("ping")) {
            event.reply("Pong!").queue();
        }
        if (event.getComponentId().equals("clear")) {
            event.getChannel().purgeMessages(event.getMessage());
        }
    }
}
