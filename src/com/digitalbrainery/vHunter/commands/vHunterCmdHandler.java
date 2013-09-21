package com.digitalbrainery.vHunter.commands;

import com.digitalbrainery.vHunter.vHunter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author FilchersSoup
 */
public class vHunterCmdHandler extends vHunter
{
	
	public boolean vHunterCommand(final CommandSender sender, final Command command, final String commandLabel, final String[] args)
	{
		if (sender instanceof Player)
		{
			
			if (args.length == 0)
			{
                            sender.sendMessage("/vHunter");
                            sender.sendMessage("      clrflag <player>");
                            sender.sendMessage("      vwarp");
                            return true;
			}
			else if (args[0].equalsIgnoreCase("clrflag") && sender.hasPermission("vhunter.clrflag"))
			{ 
				final cmdClrFlag cmd = new cmdClrFlag();
				return cmd.run(sender, args);
                        }
                        else if (args[0].equalsIgnoreCase("vwarp") && sender.hasPermission("vhunter.vwarp"))
			{ 
				final cmdvWarp cmd = new cmdvWarp();
				return cmd.run(sender, args);
                        }
                        else if (sender.hasPermission("vhunter.basic"))
                        {
                            sender.sendMessage("/vHunter");
                            sender.sendMessage("      clrflag <player>");
                            sender.sendMessage("      vwarp");
                            return true;
                        }
		}
		
		return true;		
	}
	
}
