import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import commands.ClearMessages;
import commands.HelpCommand;
import commands.music.*;
import events.MemberJoin;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot {

    private static String token = "ODI4NjcxNzM4MTQxMjEyNjgz.YGs-sg.bZc5WAY5Txyfdg9GhHinHhCGxn0";

    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA jda = JDABuilder.createDefault(token).build();

        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setPrefix("-");
        builder.setOwnerId("340660370656198657");
        builder.setHelpWord("helpme");
        builder.setActivity(Activity.listening("DJ Juninho Portugal"));
        builder.addCommands(new HelpCommand(), new PlayCommand(), new ClearMessages(), new QueueCommand(),
                new JoinCommand(), new LeaveCommand(), new SongCommand(), new StopCommand(), new SkipCommand());

        EventWaiter waiter = new EventWaiter();
        CommandClient client = builder.build();
        jda.addEventListener(waiter);
        jda.addEventListener(client);

        jda.addEventListener(new MemberJoin());

        jda.awaitReady();
    }

    /*event.getMember().getUser().openPrivateChannel().flatMap(privateChannel ->
                privateChannel.sendMessage("Test")).queue();*/

}
