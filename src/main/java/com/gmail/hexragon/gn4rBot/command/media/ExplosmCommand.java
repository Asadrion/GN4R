package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Random;

@Command(
		aliases = {"c&h", "explosm"},
		description = "Shit-posts from Explosm."
)
public class ExplosmCommand extends CommandExecutor
{
	public ExplosmCommand()
	{
		
		setDescription("We all need some satire.");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		try
		{
			Document document;
			
			int min = 1500;
			int max = 4300;
			
			int rand = min + new Random().nextInt(max - min);
			
			document = Jsoup.connect(String.format("http://explosm.net/comics/%d/", rand)).get();
			
			String builder =
					"Cyanide and Happiness" + "\n" +
							"No: **" + rand + "**\n" +
							"Link: " + document.getElementById("main-comic").absUrl("src");
			
			event.getChannel().sendMessage(builder);
			
		}
		catch (Exception e)
		{
			event.getChannel().sendMessage(String.format("%s ➤ Unable to grab Cyanide and Happiness comic.", event.getAuthor().getAsMention()));
			e.printStackTrace();
		}
	}
}
