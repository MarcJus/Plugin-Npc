package fr.marcjus.plugin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.NBTTagCompound;

public class CommandNpc implements CommandExecutor {
	
	private Principale main;

	public CommandNpc(Principale main) {
		this.main=main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			if(args.length==0){
				Player player=(Player) sender;
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
			
			if(args.length>=1){
				
				if(args[0].equalsIgnoreCase("damage")){
					if(args[1].equalsIgnoreCase("on")){
						sender.sendMessage("Les dégats sont activés sur les npc ! ");
						main.cancelDamageNPC=false;
					}else if(args[1].equalsIgnoreCase("off")){
						sender.sendMessage("Les dégats sont désactivés sur les npc ! ");
						main.cancelDamageNPC=true;
					}
				}
			}
			
			
			
		}else{
			sender.sendMessage("§cVous devez etre un jouer pour executer la commande!");
		}
		
		return false;
	}

}