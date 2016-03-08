package com.leontg77.stockup.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.HandlerList;

import com.leontg77.stockup.Main;
import com.leontg77.stockup.listeners.CraftListener;
import com.leontg77.stockup.listeners.DeathListener;

/**
 * StockUp command class.
 * 
 * @author LeonTG77
 */
public class StockUpCommand implements CommandExecutor, TabCompleter {
	private static final String PERMISSION = "stockup.manage";
	
	private final Main plugin;
	
	private final DeathListener mainListener;
	private final CraftListener compListener;
	
	/**
	 * StockUp command class constructor.
	 * 
	 * @param plugin The main class.
	 * @param mainListener The death listener class.
	 * @param compListener The craft listener class.
	 */
	public StockUpCommand(Main plugin, DeathListener mainListener, CraftListener compListener) {
		this.plugin = plugin;
		
		this.mainListener = mainListener;
		this.compListener = compListener;
	}
	
	private boolean enabled = false;
	private boolean compArrows = false;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(Main.PREFIX + "Usage: /stockup <info|enable|disable|comparrows>");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("info")) {
			sender.sendMessage(Main.PREFIX + "Scenario creator: §a/u/burningtramps");
			sender.sendMessage(Main.PREFIX + "Plugin creator: §aLeonTG77");
			sender.sendMessage(Main.PREFIX + "Version: §a" + plugin.getDescription().getVersion());
			sender.sendMessage(Main.PREFIX + "Description:");
			sender.sendMessage("§8» §f" + plugin.getDescription().getDescription());
			return true;
		}
		
		if (args[0].equalsIgnoreCase("enable")) {
			if (!sender.hasPermission(PERMISSION)) {
				sender.sendMessage(ChatColor.RED + "You don't have permission.");
				return true;
			}
			
			if (enabled) {
				sender.sendMessage(Main.PREFIX + "StockUp is already disabled.");
				return true;
			}
			
			plugin.broadcast(Main.PREFIX + "StockUp has been enabled.");
			enabled = true;
			
			Bukkit.getPluginManager().registerEvents(mainListener, plugin);
			
			if (compArrows) {
				Bukkit.getPluginManager().registerEvents(compListener, plugin);
			}
			return true;
		}

		if (args[0].equalsIgnoreCase("disable")) {
			if (!sender.hasPermission(PERMISSION)) {
				sender.sendMessage(ChatColor.RED + "You don't have permission.");
				return true;
			}
			
			if (!enabled) {
				sender.sendMessage(Main.PREFIX + "StockUp is not enabled.");
				return true;
			}

			plugin.broadcast(Main.PREFIX + "StockUp has been disabled.");
			enabled = false;
			
			HandlerList.unregisterAll(mainListener);
			
			if (compArrows) {
				HandlerList.unregisterAll(compListener);
			}
			return true;
		}

		if (args[0].equalsIgnoreCase("comparrows")) {
			if (!sender.hasPermission(PERMISSION)) {
				sender.sendMessage(ChatColor.RED + "You don't have permission.");
				return true;
			}
			
			if (!enabled) {
				sender.sendMessage(Main.PREFIX + "StockUp is not enabled.");
				return true;
			}
			
			if (compArrows) {
				plugin.broadcast(Main.PREFIX + "Compensation Arrows has been disabled.");
				compArrows = false;
				
				HandlerList.unregisterAll(compListener);
				return true;
			}

			plugin.broadcast(Main.PREFIX + "Compensation Arrows has been enabled.");
			compArrows = true;

			Bukkit.getPluginManager().registerEvents(compListener, plugin);
			return true;
		}
		
		sender.sendMessage(Main.PREFIX + "Usage: /stockup <info|enable|disable|comparrows>");
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> toReturn = new ArrayList<String>();
		List<String> list = new ArrayList<String>();
		
		if (args.length != 1) {
			return toReturn;
		}
		
		list.add("info");
		
		if (sender.hasPermission(PERMISSION)) {
			list.add("enable");
			list.add("disable");
			list.add("comparrows");
		}

		// make sure to only tab complete what starts with what they 
		// typed or everything if they didn't type anything
		for (String str : list) {
			if (args[0].isEmpty() || str.startsWith(args[0].toLowerCase())) {
				toReturn.add(str);
			}
		}
		
		return toReturn;
	}
}