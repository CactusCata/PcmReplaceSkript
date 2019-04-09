package com.gmail.cactus.cata.other;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class Sign implements Listener {

	@EventHandler
	public void setSign(SignChangeEvent event) {

		Player player = event.getPlayer();
		if (player.hasPermission("pcm.signcolor"))
			for (int i = 0; i < 4; i++)
				event.setLine(i, event.getLine(i).replace('&', '§'));
	}

}
