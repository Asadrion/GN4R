package com.gmail.hexragon.gn4rBot.command.`fun`

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command
import com.gmail.hexragon.gn4rBot.util.GnarMessage
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.text.WordUtils
import java.util.Random
import java.util.StringJoiner

@Command(aliases = arrayOf("dialog"))
class DialogCommand : CommandExecutor()
{
    override fun execute(message : GnarMessage?, args : Array<out String>?)
    {
        val joiner = StringJoiner("\n", "```", "```")
        joiner.add("﻿ ___________________________ ")
        joiner.add("| Dialog          [_][☐][✕]|")
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
        
        when (Random().nextInt(5))
        {
            0 ->
            {
                joiner.add("|   _________    ________   |")
                joiner.add("|  |   Yes   |  |   No   |  |")
                joiner.add("|   ‾‾‾‾‾‾‾‾‾    ‾‾‾‾‾‾‾‾   |")
            }
            
            1 ->
            {
                joiner.add("|  _____    ______    ____  |")
                joiner.add("| | Yes |  | What |  | No | |")
                joiner.add("|  ‾‾‾‾‾    ‾‾‾‾‾‾    ‾‾‾‾  |")
            }
            
            2 ->
            {
                joiner.add("|   _________    ________   |")
                joiner.add("|  |  Maybe  |  |( ͡° ͜ʖ ͡°)|  |")
                joiner.add("|   ‾‾‾‾‾‾‾‾‾    ‾‾‾‾‾‾‾‾   |")
            }
            
            3 ->
            {
                joiner.add("|   _____________________   |")
                joiner.add("|  |     Confirm     | X |  |")
                joiner.add("|   ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾   |")
            }
    
            4 ->
            {
                joiner.add("|   ___________   _______   |")
                joiner.add("|  |   YEAH!   | | PUSSY |  |")
                joiner.add("|   ‾‾‾‾‾‾‾‾‾‾‾   ‾‾‾‾‾‾‾   |")
            }
        }
        
        joiner.add(" ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ")
        
        try
        {
            message?.replyRaw(joiner.toString())
        }
        catch (e : UnsupportedOperationException)
        {
            message?.reply("Message was too long or something... no memes for you.")
        }
    }
}