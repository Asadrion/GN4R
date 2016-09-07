package com.gmail.hexragon.gn4rBot.command.ai;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.GuildDependent;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import org.apache.commons.lang3.StringUtils;

@GuildDependent
@Command(
        aliases = {"cbot", "cleverbot"},
        usage = "(query)",
        description = "Talk to Clever-Bot."
)
public class CleverbotCommand extends CommandExecutor
{
    private ChatterBotFactory factory = new ChatterBotFactory();
    private ChatterBot bot = null;
    private ChatterBotSession session = null;
    
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        try
        {
            if (bot == null)
            {
                bot = factory.create(ChatterBotType.CLEVERBOT);
                session = bot.createSession();
                message.reply("Clever-Bot session created for the server.");
            }
            
            String input = StringUtils.join(args, " ");
            
            String output = session.think(input);
            message.replyRaw("**[CleverBot]** ─ `" + output + "`");
        }
        catch (Exception e)
        {
            message.reply("CleverBot has encountered an exception. Resetting CleverBot.");
            bot = null;
        }
    }
}
