package com.digitalbrainery.vHunter.listeners;

import com.digitalbrainery.vHunter.commands.cmdvWarp;
import com.digitalbrainery.vHunter.vHunter;
import java.util.List;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


/**
 *
 * @author FilchersSoup
 */
public class signInteract extends vHunterListener
{
			
	public void onSignInteract(final PlayerInteractEvent event)
	{
		final Player player = event.getPlayer();

		final Sign sign = (Sign)event.getClickedBlock().getState();
		
		StringBuilder flagPath = new StringBuilder(player.getName());
		flagPath.append(".flag");
			
		final FileConfiguration pFile = vHunter.getPlayerFile().getFile();
		final FileConfiguration configFile = vHunter.getPlugin().getConfig();
				
		if ("§a<-vHunter->".equals(sign.getLine(0)) && "Enter".equalsIgnoreCase(sign.getLine(1)))
		{
			String flag = pFile.getString(flagPath.toString());			
			if (flag == null)
			{
				final Random rand1 = new Random();
				
				if(rand1.nextInt(2) == 1)
				{
					flag = "hunter";
				}
				else
				{
					flag = "vampire";
                                        player.setAllowFlight(true);
                                        player.setFlying(true);
				}
                                
                                player.sendMessage(ChatColor.GOLD + "Welcome to vHunter... " + flag.toUpperCase() + "!");
				
				pFile.set(flagPath.toString(), flag);
					
				StringBuilder equipmentPath = new StringBuilder("equipment.");
				equipmentPath.append(flag);
				equipmentPath.append(".items");
					
				StringBuilder itemName = new StringBuilder();
					
				List<String> equipmentItems = configFile.getStringList(equipmentPath.toString());
				String[] parsedEquipmentData;
				int itemId = 0;
					
				for (String equipment : equipmentItems)
				{
					parsedEquipmentData = equipment.split("[ ]+");
					for (int i = 0; i < parsedEquipmentData.length; i++)
					{
						if (i == 0)
						{
							itemId = Integer.parseInt(parsedEquipmentData[i]);
						}
						else
						{
							itemName.append(parsedEquipmentData[i]);
							if (i < parsedEquipmentData.length-1)
							{
								itemName.append(" ");
								player.updateInventory();
							}
						}
					}
						
					ItemStack item = new ItemStack(itemId);
					ItemMeta meta = (ItemMeta)item.getItemMeta();
					meta.setDisplayName(itemName.toString());
					item.setItemMeta(meta);
					player.getInventory().addItem(item);
					itemName = new StringBuilder();					
				}
                                
                                pFile.set(player.getName()+".world", player.getWorld().getName());
                                String[] args = { "vWarp" };
                                final cmdvWarp cmd = new cmdvWarp();
                                cmd.run((CommandSender)player,args);
			}
			else if(flag.equalsIgnoreCase("hunter") || flag.equalsIgnoreCase("vampire"))
			{
				StringBuilder message = new StringBuilder(ChatColor.RED + "You are already playing as a ");
				message.append(flag);
				message.append("!");
				player.sendMessage(message.toString());
			}		
		}			
		else if ("§a<-vHunter->".equals(sign.getLine(0)) && "Exit".equalsIgnoreCase(sign.getLine(1)))
		{
			String flag = pFile.getString(flagPath.toString());			
			if (flag == null)
			{
				player.sendMessage(ChatColor.RED + "You are not playing vHunter!");
			}
			else if(flag.equalsIgnoreCase("hunter") || flag.equalsIgnoreCase("vampire"))
			{		
				player.setAllowFlight(false);
                                player.setFlying(false);
                                pFile.set(flagPath.toString(), null);
				player.sendMessage(ChatColor.GOLD + "You have exited vHunter!");
                                
                                String[] args = { "vWarp", pFile.getString(player.getName()+".world") };
                                final cmdvWarp cmd = new cmdvWarp();
                                cmd.run((CommandSender)player,args);
                                pFile.set(player.getName()+".world", null);
			}
		}
	
	}
}
