package com.leontg77.stockup.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

/**
 * Craft listener class.
 * 
 * @author LeonTG77
 */
public class CraftListener implements Listener {
	
	@EventHandler
    public void on(PrepareItemCraftEvent event) {
		Recipe recipe = event.getRecipe();
		ItemStack result = recipe.getResult();
		
		if (result.getType() != Material.ARROW) {
			return;
		}
		
		CraftingInventory inv = event.getInventory();
		inv.getResult().setAmount(result.getAmount() * 4);
    }
}
