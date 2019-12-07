package fr.marcjus.plugin.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftVillager;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftZombie;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;

import com.mojang.authlib.GameProfile;

import fr.marcjus.plugin.Principale;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_12_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import net.minecraft.server.v1_12_R1.PlayerInteractManager;
import net.minecraft.server.v1_12_R1.WorldServer;

public class CommandNpc implements CommandExecutor {

	private Principale main;

	public CommandNpc(Principale main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 0) {

				Location loc = player.getLocation();
				Villager npc = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
				Entity nmsVillager = ((CraftEntity) npc).getHandle();

				nmsVillager.setCustomName("§eVillageois du chateau");
				nmsVillager.setCustomNameVisible(true);

				nmsVillager.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
				NBTTagCompound tag = new NBTTagCompound();
				nmsVillager.c(tag);
				tag.setInt("NoAI", 1);
				nmsVillager.f(tag);
			}

			if (args.length >= 1) {

				if (args[0].equalsIgnoreCase("damage")) {
					if (args[1].equalsIgnoreCase("on")) {
						sender.sendMessage("Les dégats sont activés sur les npc ! ");
						main.cancelDamageNPC = false;
					} else if (args[1].equalsIgnoreCase("off")) {
						sender.sendMessage("Les dégats sont désactivés sur les npc ! ");
						main.cancelDamageNPC = true;
					} else if (args[1] == null) {
						if (main.cancelDamageNPC == true) {
							player.sendMessage("Les dégats sont activés sur les npc ! ");
						} else {
							player.sendMessage("Les dégats sont activés sur les npc ! ");
						}
					}
				} else if (args[0].equalsIgnoreCase("nms")) {
					createNmsVillager(player, "§2Villageois de la terrasse", true);
				}else if(args[0].equalsIgnoreCase("zombie")){
					createZombiePrisoner(player, "§cDangereux criminel", false);
				}else if(args[0].equalsIgnoreCase("player")){
					createPlayer(player, "§0CustomPlayer", true);
				}
			}

		} else {
			sender.sendMessage("§cVous devez etre un jouer pour executer la commande!");
		}

		return false;
	}

	private void createPlayer(Player player, String customName, boolean customNameVisible) {
		Location loc = player.getLocation();
		MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer worldserver = ((CraftWorld) player.getWorld()).getHandle();
		GameProfile profile = new GameProfile(UUID.randomUUID(), customName);
		
		EntityPlayer npc = new EntityPlayer(nmsServer, worldserver, profile, new PlayerInteractManager(worldserver));
		Player npcPlayer = npc.getBukkitEntity().getPlayer();
		
		npcPlayer.setPlayerListName(customName);
		npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
		connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
		connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte)(npc.yaw * 256 / 360)));
		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
		
	}

	private void createNmsVillager(Player player, String customName, boolean customNameVisible) {
		Location loc = player.getLocation();
		Villager npc = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
		Entity nms = ((CraftVillager) npc).getHandle();
		nms.setCustomName(customName);
		nms.setCustomNameVisible(true);
		nms.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		
		NBTTagCompound tag = new NBTTagCompound();
		nms.c(tag);
		tag.setInt("NoAI", 1);
		nms.f(tag);
		
	}

	private void createZombiePrisoner(Player player, String customName, boolean customNameVisible){
		Location loc = player.getLocation();
		Zombie npc = (Zombie)loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
		Entity nms = ((CraftZombie) npc).getHandle();
		nms.setCustomName(customName);
		nms.setCustomNameVisible(customNameVisible);
		nms.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		
		NBTTagCompound tag = new NBTTagCompound();
		nms.c(tag);
		tag.setInt("NoAI", 1);
		nms.f(tag);
	}
}