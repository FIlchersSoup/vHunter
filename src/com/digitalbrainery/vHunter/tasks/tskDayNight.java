package com.digitalbrainery.vHunter.tasks;

import com.digitalbrainery.vHunter.vHunter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author FilchersSoup
 */
public class tskDayNight extends BukkitRunnable {

    private int id;
    private World world;
    private boolean day = false;
     
    public int getId()
    {
        return id;
    }

    public void setId(int id, World world)
    {
        this.id = id;
        this.world = world;
    }

    @Override
    public void run()
    {   
        // day = isDay() ? true : false;
        if(isDay()) {
            day = true;
            
            for (Player player : Bukkit.getServer().getOnlinePlayers())
            {    
                StringBuilder flagPath = new StringBuilder(player.getName());
                flagPath.append(".flag");
                String flag = vHunter.getPlayerFile().getFile().getString(flagPath.toString());
                
                Biome pBiome = player.getWorld().getBiome(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
                Boolean bioChk = false;
                if (pBiome.equals(Biome.BEACH) || pBiome.equals(Biome.DESERT) || pBiome.equals(Biome.DESERT_HILLS))
                {
                    bioChk = true; /* ignore hasStorm()*/
                }
                                
                if (flag != null)
                { 
                    if (flag.equalsIgnoreCase("vampire"))
                    {
                        if ((!player.getWorld().hasStorm() || bioChk) && isOutside(player))
                        {
                            player.setFireTicks(vHunter.getPlugin().getConfig().getInt("vburn"));
                        }
                    }
                } 
            }    
        } else {
            day = false;
        }
    }
    
    public boolean isDay()
    {
        return world.getTime() < 12300 || world.getTime() > 23850;

    }
    
    /* Checks if a player is outside*/
    public boolean isOutside(Player player)
    {
        int yPos = (int) player.getLocation().getY();

        for (int i = yPos + 1; i < player.getLocation().getWorld().getMaxHeight(); i++) {
            Location yLoc = new Location(player.getLocation().getWorld(), (int) player.getLocation().getX(), i, (int) player.getLocation().getZ());
            if (yLoc.getBlock() != null) {
                if (yLoc.getBlock().getType() != Material.AIR) {
                    if(yLoc.getBlock().getType() != Material.GLASS) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }


}
