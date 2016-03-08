package com.leontg77.stockup.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Death listener class.
 * 
 * @author LeonTG77
 */
public class DeathListener implements Listener {

	@EventHandler
	public void on(PlayerDeathEvent event) {
		Player player = event.getEntity();

		for (Player online : Bukkit.getOnlinePlayers()) {
			if (online == player) {
				continue;
			}
			
			online.setMaxHealth(online.getMaxHealth() + 2);
		}
	}
}