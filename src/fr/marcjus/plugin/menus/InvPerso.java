package fr.marcjus.plugin.menus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InvPerso {
	
	private Inventory inv;
	
	public void createMenu(){
		
	}
	
	public void openMenu(Player player){
		player.openInventory(inv);
	}

}
