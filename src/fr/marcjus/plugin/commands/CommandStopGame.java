package fr.marcjus.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.marcjus.plugin.GState;
import fr.marcjus.plugin.Principale;
import fr.marcjus.plugin.task.TimerGame;

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
			int sec = TimerGame.timer & 60;
			Bukkit.broadcastMessage("§2Le jeu s'est bien arrete !§e Il restait §2"+TimerGame.timer/60+"§emin et §2"+sec+"§es !");
			return true;
		}
		
		return false;
	}

}
