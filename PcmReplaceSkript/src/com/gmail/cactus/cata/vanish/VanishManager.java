package com.gmail.cactus.cata.vanish;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.cactus.cata.Main;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class VanishManager {

	private ArrayList<Player> vanished = new ArrayList<Player>();
	private int taskId;

	public ArrayList<Player> getList() {
		return vanished;
	}

	public boolean isVanished(Player player) {
		if (vanished.contains(player))
			return true;
		return false;

	}

	public void actionMessageVanish(Player playersender, Main main) {
		new BukkitRunnable() {
			private int valuemin = 4;
			private int valuemax = this.valuemin + 18;

			public void run() {
				if (VanishManager.this.vanished.contains(playersender)) {
					String corps = "    Vous êtes maintenant en Vanish !    ";
					String message = "";

					if (this.valuemin == 41) {
						this.valuemin = 0;
					}
					if (this.valuemax == 41) {
						this.valuemax = 0;
					}
					if (this.valuemin < this.valuemax) {
						message = message + corps.substring(this.valuemin, this.valuemax);
					} else {
						message = message + corps.substring(this.valuemin, corps.length());
						message = message + corps.substring(0, this.valuemax);
					}

					this.valuemin += 1;
					this.valuemax += 1;

					IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
					PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(cbc, (byte) 2);
					((CraftPlayer) playersender).getHandle().playerConnection.sendPacket(packetPlayOutChat);
				} else {
					stopSchreduler();
				}
			}
		}.runTaskTimer(main, 0L, 40L);
	}

	private void stopSchreduler() {
		Bukkit.getServer().getScheduler().cancelTask(this.taskId);
	}

}