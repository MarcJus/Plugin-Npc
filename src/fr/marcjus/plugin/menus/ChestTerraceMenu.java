package fr.marcjus.plugin.menus;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ChestTerraceMenu {
	
	private Inventory inv;
	
	public ChestTerraceMenu() {
		inv = Bukkit.createInventory(null, 27, "§eInventaires personnels");
		
	}
	
	@SuppressWarnings("deprecation")
	public void createMenu(ArrayList<Player> players){
		for(Player player : players){
			ItemStack it = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
			SkullMeta meta = (SkullMeta) it.getItemMeta();
			meta.setOwner(player.getName());
			meta.setDisplayName("§eInventaire de §2"+player.getName());
			it.setItemMeta(meta);
			inv.addItem(it);
		}
		
	}
	
	public void openInventory(Player player){
		player.openInventory(inv);
	}

}
