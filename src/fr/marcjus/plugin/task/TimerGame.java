package fr.marcjus.plugin.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.marcjus.plugin.GState;
import fr.marcjus.plugin.Principale;

public class TimerGame extends BukkitRunnable {

	private Principale main;

	public TimerGame(Principale main) {
		this.main = main;
		TimerGame.timer = main.getConfig().getInt("timergame");
	}

	public static int timer;

	@Override
	public void run() {

		int moduloTimer = timer & 60;

		if (moduloTimer == 0) {
			Bukkit.broadcastMessage("§eIl reste §2" + timer / 60 + "§e minutes !");
		}

		switch (timer) {
		case 30:
			Bukkit.broadcastMessage("§eIl reste §230§e secondes!");
			break;

		default:
			break;
		}

		if (timer <= 10) {
			Bukkit.broadcastMessage("§eIl reste §c" + timer + "§e secondes !");
		}

		if (timer == 0) {
			Bukkit.broadcastMessage("§cLe temps est fini! Le jeu est arreté");
			cancel();
			main.setState(GState.STOP);
		}

		timer--;

	}

}
