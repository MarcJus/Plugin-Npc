package fr.marcjus.plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.marcjus.plugin.menus.ChestTerraceMenu;
import fr.marcjus.plugin.menus.InvPerso;
import fr.marcjus.plugin.task.TimerTask;

public class PluginListenerMarc implements Listener {

	private Principale main;
	private Player playerBegin;
	private ArrayList<Player> playersChest = new ArrayList<>();

	public PluginListenerMarc(Principale principale) {
		this.main = principale;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (!playersChest.contains(player)) {
			playersChest.add(player);
		}
	}

	@EventHandler
	public void onInterractSign(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		Action action = event.getAction();

		if (event.getClickedBlock() != null && action == Action.RIGHT_CLICK_BLOCK) {
			BlockState bs = event.getClickedBlock().getState();
			if (bs instanceof Sign) {

				Sign sign = (Sign) bs;

				if (sign.getLine(0).equalsIgnoreCase("[Teleport]") && sign.getLine(1).equalsIgnoreCase("Bungee")) {
					if (sign.getLine(2) != null) {
						String serverName = sign.getLine(2);
						ByteArrayOutputStream b = new ByteArrayOutputStream();
						DataOutputStream out = new DataOutputStream(b);

						try {
							out.writeUTF("Connect");
							out.writeUTF(serverName);
						} catch (IOException e) {
							e.printStackTrace();
						}

						player.sendPluginMessage(main, "BungeeCord", b.toByteArray());

					}
				}

			}
		}
	}

	@EventHandler
	public void stopDamageNPC(EntityDamageEvent e) {

		Entity ent = e.getEntity();

		if (isCustomNPC(ent)) {
			e.setCancelled(main.cancelDamageNPC);

		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void interractWithNPC(PlayerInteractAtEntityEvent e) {

		Player player = e.getPlayer();
		Entity ent = e.getRightClicked();
		ItemStack it = player.getItemInHand();

		if (ent instanceof Villager) {
			Villager npc = (Villager) ent;
			if (npc.isCustomNameVisible() && npc.getCustomName() != null
					&& npc.getCustomName().equalsIgnoreCase("§eVillageois du chateau")) {
				e.setCancelled(true);

				CustomNPCMenu menu = new CustomNPCMenu(27, "§2MenuNPC");
				menu.createMenu();
				menu.openMenu(player);
			} else if (npc.isCustomNameVisible() && npc.getCustomName() != null
					&& npc.getCustomName().equals("§2Villageois de la terrasse")) {
				CustomNPCMenu menu = new CustomNPCMenu(27, "§2Villageois de la terrasse");
				ItemStack chest = new ItemStack(Material.CHEST);
				ItemMeta meta = chest.getItemMeta();
				meta.setDisplayName("§eInventaires personnels");
				chest.setItemMeta(meta);
				menu.getInv().setItem(13, chest);

				menu.openMenu(player);
			}

		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickNPCMenu(InventoryClickEvent e) {

		Player player = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		ItemStack it = e.getCurrentItem();

		if (inv != null && inv.getName().equalsIgnoreCase("§2MenuNPC")) {

			if (it == null || it.getType() == null)
				return;
			e.setCancelled(true);
			player.closeInventory();

			if (it.equals(NPCItems.PHARE.getItem())) {

				Location phare = new Location(player.getWorld(), -312, 63, 457, 90f, 0f);
				player.teleport(phare);

			} else if (it.equals(NPCItems.VILLAGE.getItem())) {
				Location village = new Location(player.getWorld(), -370, 67, 305, -90f, 0f);
				player.teleport(village);
			} else if (it.equals(NPCItems.CACHECACHE.getItem())) {
				Location cacheCache = new Location(player.getWorld(), -362, 74, 402, 179.9f, 0f);

				if (main.isState(GState.PLAYING)) {
					main.setState(GState.STARTING);
				}
				player.teleport(cacheCache);
				main.setState(GState.STARTING);
				playerBegin = player;

				TimerTask task = new TimerTask(main);
				task.runTaskTimer(main, 0, 20);

			}

		} else if (inv != null && inv.getName().equals("§2Villageois de la terrasse")) {

			if (it == null || it.getType() == null)
				return;
			e.setCancelled(true);
			player.closeInventory();
			
			
			if(it.getType().equals(Material.CHEST) && it.hasItemMeta() && it.getItemMeta().getDisplayName().equals("§eInventaires personnels")){
				Inventory invperso = Bukkit.createInventory(null, 27, "§eInventaires personnels");
				ChestTerraceMenu chestMenu = new ChestTerraceMenu();
				chestMenu.createMenu(playersChest);
				chestMenu.openInventory(player);
			}

		}else if (inv != null && inv.getName().equals("§eInventaires personnels")){
			InvPerso invperso = new InvPerso();
		}

	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {

		Player player = e.getPlayer();
		if (main.isState(GState.STARTING) && player == playerBegin) {
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		Player player = e.getPlayer();
		if (main.isState(GState.STARTING) || main.isState(GState.PLAYING)) {
			if (player == playerBegin) {
				player.sendMessage(ChatColor.DARK_RED + "Il est interdit de se téléporter ! ");
				e.setCancelled(true);
			}

		}
	}

	private boolean isCustomNPC(Entity ent) {

		if (ent instanceof Villager) {
			Villager npc = (Villager) ent;

			if (npc.isCustomNameVisible() && npc.getCustomName() != null
					&& npc.getCustomName().equalsIgnoreCase("§eVillageois du chateau")) {
				return true;
			} else if (npc.isCustomNameVisible() && npc.getCustomName().equals("§2Villageois de la terrasse")) {
				return true;
			}
		}

		return false;
	}
}
