package com.gmail.hexragon.gn4rBot.command.`fun`

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel
import com.gmail.hexragon.gn4rBot.util.GnarMessage
import net.dv8tion.jda.utils.MiscUtil
import java.awt.AlphaComposite
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList
import java.util.Collections
import javax.imageio.ImageIO

@Command(aliases = arrayOf("gusers"), description = "Fancy server stats ya uuuuuurdd mi?", permissionRequired = PermissionLevel.BOT_MASTER, showInHelp = false)
class GraphCommand : CommandExecutor()
{
    override fun execute(message : GnarMessage?, args : Array<out String>?)
    {
        message?.channel?.sendFile(drawPlot(message.time as OffsetDateTime), null)
    }
    
    fun drawPlot(now : OffsetDateTime) : File
    {
        val start = MiscUtil.getCreationTime(guild.id).toEpochSecond()
        val end = now.toEpochSecond()
        val width = 1000
        val height = 1000
        val joins = ArrayList(guild.users)
        Collections.sort(joins) { a, b -> guild.getJoinDateForUser(a).compareTo(guild.getJoinDateForUser(b)) }
        
        val buffer = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE)
        val graphic = buffer.createGraphics()
    
        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        graphic.composite = AlphaComposite.SrcOver
        
        graphic.color = Color.decode("#202020")
        graphic.fillRect(0, 0, width, height)
        
        graphic.color = Color.RED
        graphic.stroke = BasicStroke(3f)
        
        var lastX = 5
        var lastY = height
    
        for (i in joins.indices)
        {
            val x = ((guild.getJoinDateForUser(joins[i]).toEpochSecond() - start) * width / (end - start)).toInt()
            val y = height - i * height / joins.size
            graphic.drawLine(x, y, lastX, lastY)
            lastX = x
            lastY = y
        }
        
        graphic.font = Font("Century Gothic", Font.PLAIN, 24)
        
        graphic.drawString("0 - ${joins.size} Users", 20, 30)
        graphic.drawString(MiscUtil.getCreationTime(guild.id).format(DateTimeFormatter.RFC_1123_DATE_TIME), 20, 60)
        graphic.drawString(now.format(DateTimeFormatter.RFC_1123_DATE_TIME), 20, 90)
        graphic.drawString("Server: ${guild.name}", 20, 120)
        graphic.drawString("Owner: ${guild.owner.username}", 20, 150)
        
        val f = File("plot.png")
        
        try
        {
            ImageIO.write(buffer, "png", f)
        }
        catch (ex : IOException)
        {
            println("[ERROR] An error occured drawing the plot.")
        }
    
        return f
    }
}