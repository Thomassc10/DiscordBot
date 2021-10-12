package me.thomas.bot.events;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberJoin extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        if (event.getGuild().getRoles().contains("Random"))
            event.getGuild().addRoleToMember(event.getMember().getId(), event.getGuild().getRoleById("821810021008408626"));
    }
}
