package com.digitalbrainery.vHunter.commands;

import com.digitalbrainery.vHunter.vHunter;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author FilchersSoup
 */
public class cmdClrFlag extends vHunterCmdHandler
{
	public boolean run (final CommandSender sender, final String[] args)
	{
		
		if (args.length > 1)
		{
			Player target = sender.getServer().getPlayer(args[1]);
			
			if (target != null)
			{
			StringBuilder flagPath = new StringBuilder(target.getName());
			flagPath.append(".flag");
                        
			target.setAllowFlight(false);
                        target.setFlying(false);
                        
			final FileConfiguration pFile = vHunter.getPlayerFile().getFile();			
			pFile.set(flagPath.toString(), null);
		
			StringBuilder message = new StringBuilder("You have removed ");
			message.append(target.getName());
			message.append(" from vHunter!");			
			sender.sendMessage(message.toString());
			
			target.sendMessage("You have been removed from vHunter!");
			return true;
			}
			
			sender.sendMessage("That player does not exists!");
			return true;
		}
		
		sender.sendMessage("/vHunter clrflag <player>");
		return true;
	}
	
}
