package com.gmail.cactus.cata.commands.other;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.Main;
import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;
import com.gmail.cactus.cata.maths.Maths;

import net.minecraft.server.v1_8_R3.EntityPlayer;

public class Tps implements CommandExecutor, Runnable {

	private static int TICK_COUNT = 0;
	private static long[] TICKS = new long[600];
	private Main main;
	private CommandManager cmdManag;

	public Tps(Main main, CommandManager cmdManag) {
		this.main = main;
		this.cmdManag = cmdManag;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("lag") || cmd.getName().equalsIgnoreCase("ping")) {

			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			if (args.length > 0) {

				Player target = Bukkit.getPlayerExact(args[0]);
				if (target != null && target.isOnline()) {

					sender.sendMessage("----LAG-PLAYER-" + target.getDisplayName() + "§f----");
					sender.sendMessage(
							"§3Latence du joueur " + target.getDisplayName() + "§3 : " + getMs(target) + "§3 ms !");
					return true;

				}

				sender.sendMessage(PrefixMessage.PREFIX + "Le joueur " + args[0] + " n'est pas en ligne !");
				return true;

			}

			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(main, this, 100L, 1L);
			sender.sendMessage(ChatColor.BOLD + "--------LAG--------");
			sender.sendMessage("§3Tps serveur : §e" + Maths.arrondidouble(getTPS(), 3));
			sender.sendMessage("§3Tps serveur en % : §e" + Math.round((1.0D - getTPS() / 20.0D) * 100.0D) + " %");

			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.sendMessage("§3Latence avec le server : §e" + getMs(player) + "§3 ms !");
				return true;
			}

			return true;

		}

		return false;
	}

	private double getTPS() {
		double tps = getTPS(100);
		if (tps > 20)
			tps = 20;
		return tps;
	}

	private double getTPS(int ticks) {
		if (TICK_COUNT < ticks) {
			return 20.0D;
		}
		int target = (TICK_COUNT - 1 - ticks) % TICKS.length;
		long elapsed = System.currentTimeMillis() - TICKS[target];

		return ticks / (elapsed / 1000.0D);
	}

	public void run() {
		TICKS[(TICK_COUNT % TICKS.length)] = System.currentTimeMillis();

		TICK_COUNT += 1;
	}

	private int getMs(Player player) {

		CraftPlayer cp = (CraftPlayer) player;
		EntityPlayer ep = cp.getHandle();
		return ep.ping;

	}

}
