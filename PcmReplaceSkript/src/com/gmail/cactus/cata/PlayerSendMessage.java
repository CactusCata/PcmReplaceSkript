package com.gmail.cactus.cata;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.gmail.cactus.cata.enums.PrefixMessage;
import com.gmail.cactus.cata.vanish.VanishManager;

public class PlayerSendMessage implements Listener {

	private Main main;
	private VanishManager vanishManag;
	private GameObj gameObj;

	public PlayerSendMessage(Main main, GameObj gameObj, VanishManager vanishManag) {
		this.main = main;
		this.gameObj = gameObj;
		this.vanishManag = vanishManag;
	}

	@EventHandler
	public void SendMessage(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		event.setCancelled(true);
		if (vanishManag.getList().contains(player)) {

			if (!(main.getConfig().getBoolean("Vanish.CanSpeak"))) {

				player.sendMessage(PrefixMessage.PREFIX_VANISH
						+ "La config vous empeche de parler, vous pouvez executer la commande suivante pour parler à nouveau :\n/config change CanSpeak");
				return;

			}

		}

		String[] info = gameObj.getMapInformation().get(player);

		try {
			if (player.hasPermission("pcm.msg.color")) {
				Bukkit.broadcastMessage(
						info[0] + player.getName() + info[1] + ": " + event.getMessage().replace('&', '§'));

			} else {
				Bukkit.broadcastMessage(info[0] + player.getName() + info[1] + ": " + event.getMessage());
			}
		} catch (NullPointerException e) {
			gameObj.reloadFixing();
			player.sendMessage(PrefixMessage.PREFIX + "Fixage du chat et des grades !");
			event.setCancelled(true);

		}

	}

}
