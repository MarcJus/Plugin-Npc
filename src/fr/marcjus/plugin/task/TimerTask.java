package fr.marcjus.plugin.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.marcjus.plugin.GState;
import fr.marcjus.plugin.Principale;

public class TimerTask extends BukkitRunnable {

	private int timer;

	private Principale main;

	public TimerTask(Principale main) {

		this.main = main;
		this.timer = main.getConfig().getInt("timertask");

	}

	@Override
	public void run() {

		if (main.isState(GState.STOP))
			return;

		if (main.isState(GState.PLAYING))
			return;

		if (timer == 30) {
			Bukkit.broadcastMessage("§cAttention il est interdit de se téléporter!");
		}

		if (timer > 10) {
			Bukkit.broadcastMessage("Il reste plus que §2" + timer + " s §rpour se cacher");
		} else if (timer <= 10) {
			Bukkit.broadcastMessage("Il reste plus que §c" + timer + " s §rpour se cacher");
		}

		if (timer == 0) {
			Bukkit.broadcastMessage("Le temps est fini! Bon jeu!");
			main.setState(GState.PLAYING);
			TimerGame game = new TimerGame(main);
			game.runTaskTimer(main, 0, 20);
			cancel();
		}
		timer--;

	}

}
