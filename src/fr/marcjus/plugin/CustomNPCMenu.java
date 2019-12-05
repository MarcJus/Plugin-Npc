package fr.marcjus.plugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CustomNPCMenu {

	private Inventory inv;

	public CustomNPCMenu(int size, String name) {

		if (size == 0) {
			size = 9;
		} else if (size > 54) {
			size = 54;
		}

		inv = Bukkit.createInventory(null, size, name);
	}

	public void createMenu() {

		for (NPCItems item : NPCItems.values()) {
			inv.setItem(item.getSlot(), item.getItem());
		}

	}

	public void openMenu(Player player) {
		player.openInventory(inv);
	}

}
