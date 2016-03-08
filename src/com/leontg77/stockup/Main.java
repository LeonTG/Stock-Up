package com.leontg77.stockup;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.leontg77.stockup.commands.StockUpCommand;
import com.leontg77.stockup.listeners.CraftListener;
import com.leontg77.stockup.listeners.DeathListener;

/**
 * Main class of the plugin.
 * 
 * @author LeonTG77
 */
public class Main extends JavaPlugin {
	public static final String PREFIX = "§6Stock Up §8» §7";

	@Override
	public void onDisable() {
		PluginDescriptionFile file = getDescription();
		getLogger().info(file.getName() + " has been disabled.");
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile file = getDescription();
		getLogger().info(file.getName() + " v" + file.getVersion() + " has been enabled.");
		getLogger().info("The plugin is made by LeonTG77.");
		
		DeathListener death = new DeathListener();
		CraftListener craft = new CraftListener();
		
		StockUpCommand command = new StockUpCommand(this, death, craft);
		
		// register command.
		getCommand("stockup").setExecutor(command);
		getCommand("stockup").setTabCompleter(command);
	}
	
	/**
	 * Broadcasts a message to everyone online.
	 * 
	 * @param message the message.
	 */
	public void broadcast(String message) {
		for (Player online : Bukkit.getOnlinePlayers()) {
			online.sendMessage(message);
		}
		
		Bukkit.getLogger().info(message);
	}
}