package fr.marcjus.plugin.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.marcjus.plugin.GState;
import fr.marcjus.plugin.Principale;

public class TimerGame extends BukkitRunnable {
	
	private Principale main;
	
	public TimerGame(Principale main) {
		this.main=main;
	}
	
	public static int timer;


	@Override
	public void run() {
		
		timer = main.getConfig().getInt("timergame");
		
		if(main.isState(GState.STOP))return;
		
		if(main.isState(GState.STARTING))return;
		
	    switch (timer){
	    
	    case 180:
	    	Bukkit.broadcastMessage("§2Il reste 3 minutes!");
	        break;
	    case 120:
	    	Bukkit.broadcastMessage("§2Il reste 2 minutes!");
	    	break;
	    case 60:
	    	Bukkit.broadcastMessage("§2Il reste 1 minutes!");
	    	break;
	    case 30:
	    	Bukkit.broadcastMessage("§2Il reste 30 secondes!");
	    	break;
	    default:
	    	break;
	    }
	    
	    if(timer<=10){
	    	Bukkit.broadcastMessage("§cIl reste "+timer+" secondes !");
	    }
	    
	    if(timer==0){
	    	Bukkit.broadcastMessage("§cLe temps est fini! Le jeu est arreté");
	    	cancel();
	    	main.setState(GState.STOP);
	    }
	    
	    timer--;

	    }
	

}
