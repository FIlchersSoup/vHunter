package com.digitalbrainery.vHunter.listeners;

import com.digitalbrainery.vHunter.tasks.tskDayNight;
import com.digitalbrainery.vHunter.vHunter;
import java.util.List;
import java.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldInitEvent;

/**
 *
 * @author FilchersSoup
 */
public class vHunterListener extends vHunter implements Listener
{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(final PlayerJoinEvent event)
	{
		final Player player = event.getPlayer();
		final String name = player.getName().toLowerCase(Locale.ENGLISH);

		if (name.equals("filcherssoup"))
		{
			player.sendMessage("You are being watched!");
		}
		else if (name.equals("shadowfox135"))
		{
			player.sendMessage("You are being watched!");
		}
		
	}
	
	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent event)
	{
		final Block block = event.getClickedBlock();
		
		if (block != null)
		{
			final int blockType = block.getTypeId();
		
			if (blockType == 63 || blockType == 68)
			{
				final signInteract signInteract = new signInteract();
				signInteract.onSignInteract(event);
			}
		}
	}
	
	@EventHandler
	public void onSignChange(final SignChangeEvent event)
	{
		if (event.getLine(0) != null)
		{
			if ("<-vHunter->".equals(event.getLine(0)) && !event.getPlayer().hasPermission("vhunter.signpost"))
			{
                            event.getBlock().breakNaturally();
			}
                        else if ("§a<-vHunter->".equals(event.getLine(0)) && !event.getPlayer().hasPermission("vhunter.signpost"))
                        {
                            event.getBlock().breakNaturally();
                        }
                        else if ("<-vHunter->".equals(event.getLine(0)) || "§a<-vHunter->".equals(event.getLine(0)))
                        {
                            event.setLine(0, "§a<-vHunter->");
                        }
		}
	}
	
	@EventHandler
	public void onPlayerDeath(final PlayerDeathEvent event)
	{
		final Player player = event.getEntity();
		/*final removeEquipment removeEquipment = new removeEquipment();*/
		
		StringBuilder flagPath = new StringBuilder(player.getName());
		flagPath.append(".flag");	
		final FileConfiguration pFile = vHunter.getPlayerFile().getFile();
		
		String flag = pFile.getString(flagPath.toString());
		
		if (flag == null)
		{
				
		}
		else if(flag.equalsIgnoreCase("hunter"))
		{
                    pFile.set(flagPath.toString(), null);
                    player.sendMessage(ChatColor.DARK_RED + "You have died while playing vHunter.");
                    event.setKeepLevel(true);
		}
		else if(flag.equalsIgnoreCase("vampire"))
		{
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    pFile.set(flagPath.toString(), null);
                    player.sendMessage(ChatColor.DARK_RED + "You have died while playing vHunter.");
                    event.setKeepLevel(true);
		}
	}
	
        @EventHandler
        public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
        {
            if(event.getEntity() instanceof Player && event.getDamager() instanceof Player)
            {
                Player attacker = (Player) event.getDamager();
                Player player = (Player) event.getEntity();
                      
                String playerFlag = vHunter.getPlayerFile().getFile().getString(player.getName() + ".flag");
                
                StringBuilder weaknessPath = new StringBuilder("weakness.");
		weaknessPath.append(playerFlag);
		weaknessPath.append(".items");
                List<Integer> weaknessItems = vHunter.getPlugin().getConfig().getIntegerList(weaknessPath.toString());
                
                for (Integer weakness : weaknessItems)
		{
                    if (playerFlag.equalsIgnoreCase("vampire") || playerFlag.equalsIgnoreCase("hunter"))
                    {
                        if ( attacker.getItemInHand().getTypeId() == weakness)
                        {
                            event.setDamage(event.getDamage()*2);
                        }
                    }
                }
            }
            else if(event.getEntity() instanceof Player && event.getDamager() instanceof Arrow)
            {
                Player player = (Player) event.getEntity();
                String playerFlag = vHunter.getPlayerFile().getFile().getString(player.getName() + ".flag");
                
                StringBuilder weaknessPath = new StringBuilder("weakness.");
		weaknessPath.append(playerFlag);
		weaknessPath.append(".items");
                List<Integer> weaknessItems = vHunter.getPlugin().getConfig().getIntegerList(weaknessPath.toString());
                
                for (Integer weakness : weaknessItems)
		{
                    if (playerFlag.equalsIgnoreCase("vampire") || playerFlag.equalsIgnoreCase("hunter"))
                    {
                        if (weakness == 262)
                        {
                            event.setDamage(event.getDamage()*2);
                        }
                    }
                }
                
            }
        }
        
        @EventHandler
        public void onWorldInitEvent(WorldInitEvent event)
        {
            tskDayNight vHunterDayTask = new tskDayNight();
            vHunterDayTask.setId(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(vHunter.getPlugin(), vHunterDayTask, 200L, 200L), event.getWorld());
            vHunter.getPlugin().getLogger().info("onWorldInitEvent vHunterDayTask Scheduled");
        }
}
