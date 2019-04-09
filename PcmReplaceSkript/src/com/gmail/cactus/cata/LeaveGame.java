package com.gmail.cactus.cata;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

import com.gmail.cactus.cata.commands.spy.MessageObj;
import com.gmail.cactus.cata.maths.Maths;
import com.gmail.cactus.cata.vanish.VanishManager;

public class LeaveGame implements Listener {

	private Main main;
	private MessageObj messageObj;
	private GameObj gameObj;
	private VanishManager vanishManager;

	public LeaveGame(Main main, GameObj gameObj, MessageObj messageObj, VanishManager vanishManager) {
		this.main = main;
		this.gameObj = gameObj;
		this.messageObj = messageObj;
		this.vanishManager = vanishManager;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {

		Player player = event.getPlayer();
		double x = Maths.arrondidouble(player.getLocation().getX(), 2);
		double y = Maths.arrondidouble(player.getLocation().getY(), 2);
		double z = Maths.arrondidouble(player.getLocation().getZ(), 2);
		double pitch = Maths.arrondifloat(player.getLocation().getPitch(), 2);
		double yaw = Maths.arrondifloat(player.getLocation().getYaw(), 2);

		event.setQuitMessage("");
		if (messageObj.getSocial().contains(player.getName()))
			messageObj.getSocial().remove(player.getName());

		File file = new File(main.getDataFolder(), "players/" + player.getUniqueId().toString() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		Calendar calendrier = Calendar.getInstance();

		config.set("general.lastlogin",
				calendrier.get(Calendar.HOUR_OF_DAY) + ":" + calendrier.get(Calendar.MINUTE) + ":"
						+ calendrier.get(Calendar.SECOND) + " " + calendrier.get(Calendar.DAY_OF_MONTH) + "/"
						+ calendrier.get(Calendar.MONTH) + 1 + "/" + calendrier.get(Calendar.YEAR));
		config.set("stats.lastposition.x", x);
		config.set("stats.lastposition.y", y);
		config.set("stats.lastposition.z", z);
		config.set("stats.lastposition.pitch", pitch);
		config.set("stats.lastposition.yaw", yaw);

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!vanishManager.getList().contains(player))
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);

		gameObj.getMapInformation().remove(player);

	}

}
