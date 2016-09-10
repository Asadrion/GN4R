package com.gmail.hexragon.gn4rBot.managers.commands;

import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;

import java.lang.reflect.Field;
import java.util.*;

public class CommandRegistry
{
    private Map<String, CommandExecutor> registry = new LinkedHashMap<>();
    
    public void registerCommand(Class<? extends CommandExecutor> cls)
    {
        if (!cls.isAnnotationPresent(Command.class))
        {
            throw new IllegalStateException("@Command annotation not found for class: "+cls.getName());
        }
    
        try
        {
            CommandExecutor cmd = cls.newInstance();
        
            Command meta = cls.getAnnotation(Command.class);
        
            cmd.setDescription(meta.description());
            cmd.setPermission(meta.permissionRequired());
            cmd.showInHelp(meta.showInHelp());
            cmd.setUsage(meta.usage());
        
            for (Field field : cmd.getClass().getSuperclass().getDeclaredFields())
            {
                if (field.getType().isAssignableFrom(CommandManager.class))
                {
                    field.setAccessible(true);
                    field.set(cmd, this);
                }
            }
        
            Arrays.stream(meta.aliases()).forEach(s -> registerCommand(s, cmd));
        }
        catch (IllegalAccessException | InstantiationException e)
        {
            e.printStackTrace();
        }
    }
    
    public void registerCommand(String label, CommandExecutor cmd)
    {
        if (!registry.isEmpty())
        {
            for (String command : registry.keySet())
            {
                if (label.equals(command))
                {
                    //System.out.println("WARNING: Command " + label + " is already registered.");
                    throw new IllegalStateException("Command " + label + " is already registered.");
                }
            }
        }
    
        registry.put(label, cmd);
    }
    
    public void unregisterCommand(String label) //throws IllegalStateException
    {
        if (!registry.isEmpty())
        {
            registry.keySet().stream()
                    .filter(label::equals)
                    .forEach(command -> registry.remove(label));
        }
        //throw new IllegalStateException("Command " + label + " isn't registered.");
    }
    
    public Map<String, CommandExecutor> getRegistry()
    {
        return registry;
    }
    
    public Map<String, CommandExecutor> getUniqueRegistry()
    {
        Set<CommandExecutor> blacklist = new HashSet<>();
        Map<String, CommandExecutor> uniqueMap = new LinkedHashMap<>();
    
        registry.entrySet().stream()
                .filter(entry -> !blacklist.contains(entry.getValue()))
                .forEach(entry ->
                {
                    uniqueMap.put(entry.getKey(), entry.getValue());
                    blacklist.add(entry.getValue());
                });
    
        return uniqueMap;
    }
    
    public CommandExecutor getCommand(String key)
    {
        return getRegistry().get(key);
    }
    
    public CommandExecutor getCommand(Class<? extends CommandExecutor> cls)
    {
        Optional<CommandExecutor> cmd = registry.values().stream()
                .filter(commandExecutor -> commandExecutor.getClass() == cls)
                .findFirst();
        
        return cmd.isPresent() ? cmd.get() : null;
    }
}
