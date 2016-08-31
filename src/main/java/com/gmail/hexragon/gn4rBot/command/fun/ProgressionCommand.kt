package com.gmail.hexragon.gn4rBot.command.`fun`

import com.gmail.hexragon.gn4rBot.GnarBot
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel
import com.gmail.hexragon.gn4rBot.util.GnarMessage
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.text.WordUtils
import java.util.StringJoiner
import java.util.concurrent.TimeUnit

@Command(aliases = arrayOf("progress"), permissionRequired = PermissionLevel.BOT_MASTER, showInHelp = false)
class ProgressionCommand : CommandExecutor()
{
    override fun execute(message : GnarMessage?, args : Array<out String>?)
    {
        val joiner = StringJoiner("\n", "```", "```")
        joiner.add("﻿ ___________________________ ")
        joiner.add("| Progression     [_][☐][✕]|")
        joiner.add("|‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾|")
        
        val lines = WordUtils
                .wrap(StringUtils.join(args, ' ').replace("```",""), 25)
                .split("\n")
        
        lines.forEach {
            val builder = StringBuilder()
            
            repeat(25 - it.trim().length) { builder.append(' ') }
            
            var str = it.trim()
            
            if (str.length > 25)
            {
                str = "${str.substring(0, 22)}..."
            }
            
            joiner.add("| $str$builder |")
        }

        
        joiner.add("| ____________________      |")
        joiner.add("||var-A| var-B |")
        joiner.add("| ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾      |")
        joiner.add(" ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ")
        
        val list = arrayListOf<String>()
        
        for (i in 0..20)
        {
            val builder = StringBuilder()
            repeat(20) {
                if (it < i+1) builder.append('█')
                else builder.append(' ')
            }
            
            var percent = (i * 5).toString()
            while (percent.length < 3)
            {
                percent = " $percent"
            }
            
            list.add(joiner
                    .toString()
                    .replace("var-A", builder.toString())
                    .replace("var-B", percent))
        }
    
        try
        {
            val msg = message?.replyRaw(list[0])
    
            list.forEachIndexed { i, s ->
                GnarBot.scheduler.schedule({
                    msg?.updateMessage(list[i])
                }, i + 1L, TimeUnit.SECONDS)
            }
        }
        catch (e : UnsupportedOperationException)
        {
            message?.reply("Message was too long or something... no memes for you.")
        }
    }
}