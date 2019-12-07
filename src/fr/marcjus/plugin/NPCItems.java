package fr.marcjus.plugin;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum NPCItems {

	CACHECACHE(11, new ItemStack(Material.GOLD_BLOCK), "§6§lCache Cache", "§cDémarre une partie de cache cache"), PHARE(
			13, new ItemStack(Material.BOAT), "§6§lPhare", "§cPour aller au phare"), VILLAGE(15,
					new ItemStack(Material.WHEAT), "§6§lVillage", "§cPour aller au village");

	private int slot;
	private ItemStack it;
	private String name;
	private String lore;

	NPCItems(int slot, ItemStack it, String name, String lore) {
		this.setSlot(slot);
		this.setIt(it);
		this.setName(name);
		this.setLore(lore);
	}

	public ItemStack getItem() {
		ItemStack i = it;
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.setLore(Arrays.asList(lore));
		i.setItemMeta(im);
		return i;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public ItemStack getIt() {
		return it;
	}

	public void setIt(ItemStack it) {
		this.it = it;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLore() {
		return lore;
	}

	public void setLore(String lore) {
		this.lore = lore;
	}

}
