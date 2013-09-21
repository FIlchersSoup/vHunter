package com.digitalbrainery.vHunter;

import java.io.File;
import org.bukkit.WorldCreator;

/**
 *
 * @author FilchersSoup
 */
public class vHunterWorld {
    
    public void loadWorlds(){
        
        String worldName = vHunter.getPlugin().getConfig().getString("world");
        
        if ( worldName != null)
        {
            if(new File(worldName + "/level.dat").exists()){
                vHunter.getPlugin().getServer().createWorld(new WorldCreator(worldName));
            }
            else
            {
                vHunter.getPlugin().getLogger().info("SEVERE - Could not find vHunter world!");
            }
        }
        
    }
    
}
