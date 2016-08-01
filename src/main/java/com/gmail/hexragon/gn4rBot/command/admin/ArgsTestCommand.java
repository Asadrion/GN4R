package com.gmail.hexragon.gn4rBot.command.admin;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.Message;
import org.apache.commons.lang3.StringUtils;

@Command(
		aliases = "args",
		permissionRequired = PermissionLevel.BOT_MASTER,
		showInHelp = false
)
public class ArgsTestCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		message.getChannel().sendMessage("Args: `[" + StringUtils.join(args, ", ") + "]`.");
	}
}