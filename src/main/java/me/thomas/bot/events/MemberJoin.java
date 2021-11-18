package me.thomas.bot.events;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberJoin extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        Guild guild = event.getGuild();
        if (hasRole(guild, "Random"))
            guild.addRoleToMember(event.getMember().getId(), guild.getRoleById("821810021008408626"));
        if (hasRole(guild, "Convidado"))
            guild.addRoleToMember(event.getMember().getId(), guild.getRoleById("891367701744848968"));
    }

    private boolean hasRole(Guild guild, String role){
        for (Role r : guild.getRoles()){
            if (r.getName().contains(role))
                return true;
        }
        return false;
    }

}
