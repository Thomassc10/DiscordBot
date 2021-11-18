package me.thomas.bot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.thomas.bot.commands.ClearMessages;
import me.thomas.bot.commands.HelpCommand;
import me.thomas.bot.commands.SuggestionCommand;
import me.thomas.bot.commands.music.*;
import me.thomas.bot.events.BotJoinEvent;
import me.thomas.bot.events.ButtonClick;
import me.thomas.bot.events.MemberJoin;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.Scanner;

public class Bot {

    private static JDA jda;

    public static void main(String[] args) throws LoginException, InterruptedException {
        String token = "";
        String bot = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bot: ");
        bot = scanner.next();
        if (bot.equalsIgnoreCase("main"))
            token = "ODI4NjcxNzM4MTQxMjEyNjgz.YGs-sg.bZc5WAY5Txyfdg9GhHinHhCGxn0";
        else if (bot.equalsIgnoreCase("test"))
            token = "ODk2MDczMDI1MTYwNjM4NTc1.YWBzCQ.nmL1FZpn-O1jMiFXPo7ovjHK8_4";

        jda = JDABuilder.createDefault(token).disableIntents(GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.GUILD_INVITES,
                GatewayIntent.GUILD_BANS, GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.DIRECT_MESSAGES).build();

        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setPrefix("-");
        builder.setOwnerId("340660370656198657");
        builder.setHelpWord("helpme");
        builder.setActivity(Activity.listening("DJ Juninho Portugal"));
        builder.addCommands(new HelpCommand(), new PlayCommand(), new ClearMessages(), new QueueCommand(),
                new JoinCommand(), new LeaveCommand(), new SongCommand(), new StopCommand(), new SkipCommand(),
                new ShuffleCommand(), new ClearCommand(), new SuggestionCommand(), new PlayNextCommand(),
                new LyricsCommand());

        EventWaiter waiter = new EventWaiter();
        CommandClient client = builder.build();
        jda.addEventListener(waiter);
        jda.addEventListener(client);

        jda.addEventListener(new MemberJoin());
        jda.addEventListener(new BotJoinEvent());
        jda.addEventListener(new ButtonClick());

        jda.setAutoReconnect(true);
        jda.awaitReady();
        System.out.println("Bot is online!");
    }

    public static JDA getJda(){
        return jda;
    }

    /*public static Properties getConfig() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(new File(" "));
        properties.load(fileInputStream);
        return properties;
    }*/

}
