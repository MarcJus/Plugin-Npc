package fr.marcjus.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import fr.marcjus.plugin.CommandNpc;

public class Principale extends JavaPlugin{
	
	public static Principale instance;
	
	public static Principale getInstance(){
		return instance;
	}
	
	private GState state;
	public boolean cancelDamageNPC=true;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		setState(GState.STOP);
		
		getServer().getPluginManager().registerEvents(new PluginListenerMarc(this), this);
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getCommand("npc").setExecutor(new CommandNpc(this));
		getCommand("gamestop").setExecutor(new CommandStopGame(this));
	}
	
	@Override
	public void onDisable() {
		
	}

	public boolean isState(GState state) {
		return this.state==state;
	}

	public void setState(GState state) {
		this.state = state;
	}


}
