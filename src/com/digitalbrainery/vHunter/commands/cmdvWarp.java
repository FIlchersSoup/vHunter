package com.digitalbrainery.vHunter.commands;

import com.digitalbrainery.vHunter.vHunter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author FilchesSoup
 */
public class cmdvWarp {
    
    public boolean run (final CommandSender sender, final String[] args)
    {
            
            String worldName = vHunter.getPlugin().getConfig().getString("world");
            Player player = (Player) sender;
            
            if (args.length < 1) 
            {
                player.sendMessage("/vHunter vWarp <world>");
		return true;
            }
            if (worldName != null)
            {
                if(args.length < 2)
                {
                    if (player.getServer().getWorld(worldName) != null)
                    {
                        player.teleport(player.getServer().getWorld(worldName).getSpawnLocation());
                        player.sendMessage(ChatColor.RED + "Welcome to " + worldName);
                        return true;
                    }
                }
                else if (player.getServer().getWorld(args[1]) != null && !player.getWorld().getName().equals(args[1]))
                {
                    player.teleport(player.getServer().getWorld(args[1]).getSpawnLocation());
                    player.sendMessage(ChatColor.RED + "Welcome to " + args[1]);
                    return true;
                }
                else if (player.getWorld().getName().equals(args[1]))
                {
                    sender.sendMessage(ChatColor.RED + "You can't use this command to return to spawn.");
                }
                /*else{
                    sender.sendMessage(ChatColor.RED + "That world does not exist.");
                }*/
            }
            return true;
    }
}
