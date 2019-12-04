package fr.marcjus.plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.marcjus.plugin.GState;
import fr.marcjus.plugin.Principale;
import fr.marcjus.plugin.TimerGame;

public class CommandGetTime implements CommandExecutor {

	private Principale main;

	public CommandGetTime(Principale main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("gettime")) {
			if (main.isState(GState.PLAYING)) {
				int sec = TimerGame.timer & 60;
				sender.sendMessage("§eIl reste §2" + TimerGame.timer / 60 + "§emin et §2" + sec + "§es !");
			} else if (main.isState(GState.STARTING)) {
				sender.sendMessage("§cLe jeu est en train de démarrer !");
			} else if (main.isState(GState.STOP)) {
				sender.sendMessage("§cLe jeu est fini !");
			}

		}
		return false;
	}

}
