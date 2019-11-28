package fr.marcjus.plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStopGame implements CommandExecutor {
	
	private Principale main;

	public CommandStopGame(Principale main) {
		this.main=main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(main.isState(GState.STOP)){
			sender.sendMessage("§cLe jeu n'a pas commence");
		}else{
			main.setState(GState.STOP);
			Bukkit.broadcastMessage("§2Le jeu s'est bien arrete");
			return true;
			
			
			
		}
		
		
		
		return false;
	}

}
