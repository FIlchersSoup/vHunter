package com.digitalbrainery.vHunter;

import com.digitalbrainery.vHunter.commands.vHunterCmdHandler;
import com.digitalbrainery.vHunter.listeners.vHunterListener;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author FilchersSoup
 */
public class vHunter extends JavaPlugin {
 
	private static vHunterPlayerFile playerFile = new vHunterPlayerFile();
	private static Plugin plugin;
        final vHunterWorld vHunterWorld = new vHunterWorld();
	
	@Override
	public void onEnable(){
		plugin = this;
		reload();
		playerFile.PlayerFileAccessor(this,"vHunter.yml");
		playerFile.saveDefaultFile();
		this.saveDefaultConfig();
		getLogger().info("vHunter has been enabled!");
                vHunterWorld.loadWorlds();               
	}
 
	@Override
	public void onDisable(){
		playerFile.saveFile();
		this.saveConfig();
		getLogger().info("vHunter has been disabled!");
	}
	
	public void reload()
	{
		final PluginManager pm = getServer().getPluginManager();
		registerListeners(pm);
	}
	
	private void registerListeners(PluginManager pm)
	{

		final vHunterListener pListener = new vHunterListener();
		pm.registerEvents(pListener, this);
		
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String commandLabel, final String[] args)
	{
		if(command.getName().equalsIgnoreCase("vHunter"))
		{
			final vHunterCmdHandler cmdHandler = new vHunterCmdHandler();
			return cmdHandler.vHunterCommand(sender, command, commandLabel, args);
		}
				
		return false;
	}
	
	public static Plugin getPlugin() 
	{
            return plugin;
	}
	
	public static vHunterPlayerFile getPlayerFile() 
	{
            return playerFile;
	}
}