package fr.marcjus.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.marcjus.plugin.commands.CommandGetTime;
import fr.marcjus.plugin.commands.CommandNpc;
import fr.marcjus.plugin.commands.CommandStopGame;

public class Principale extends JavaPlugin {

	private GState state;
	public boolean cancelDamageNPC = true;
	private ArrayList<Player> playersChest = new ArrayList<>();
	private ArrayList<Location> locsChest = new ArrayList<>();
	private Map<Player, Location> playersLocChest = new HashMap<>();

	@Override
	public void onEnable() {
		saveDefaultConfig();
		setState(GState.STOP);
		addPlayerChest();

		getServer().getPluginManager().registerEvents(new PluginListenerNpc(this), this);
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getCommand("npc").setExecutor(new CommandNpc(this));
		getCommand("gamestop").setExecutor(new CommandStopGame(this));
		getCommand("gettime").setExecutor(new CommandGetTime(this));
	}

	private void addPlayerChest() {
		for(Player player : Bukkit.getOnlinePlayers()){
			World world = Bukkit.getWorld("world");
			locsChest.add(new Location(world, -387, 72, 369));
			locsChest.add(new Location(world, -387, 72, 371));
			locsChest.add(new Location(world, -387, 72, 367));
			for(int i = 0; i<locsChest.size(); i++){
				playersLocChest.put(player, locsChest.get(i));
			}
			
		}
	}

	@Override
	public void onDisable() {

	}

	public boolean isState(GState state) {
		return this.state == state;
	}

	public void setState(GState state) {
		this.state = state;
	}
	
	public ArrayList<Player> getPlayersChest(){
		return playersChest;
	}

	public ArrayList<Location> getLocsChest() {
		return locsChest;
	}

	public Map<Player, Location> getPlayersLocChest() {
		return playersLocChest;
	}

}
