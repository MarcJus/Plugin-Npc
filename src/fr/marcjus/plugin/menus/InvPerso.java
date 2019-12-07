package fr.marcjus.plugin.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InvPerso {
	
	private Inventory inv;
	
	public void createMenu(Player player){
		inv = Bukkit.createInventory(null, 27, "§2Inventaire perso de §2"+player.getName());
	}
	
	public void openMenu(Player player){
		player.openInventory(inv);
	}

}
