package com.gmail.cactus.cata.commands.other;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class Killb implements CommandExecutor {

	private CommandManager cmdManag;

	public Killb(CommandManager cmdManag) {
		this.cmdManag = cmdManag;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("killb")) {

			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			if (args.length == 0) {
				sender.sendMessage(PrefixMessage.PREFIX + "Veillez préciser le joueur !");
				return true;
			}

			if (args.length == 1) {
				sender.sendMessage(PrefixMessage.PREFIX + "Veillez préciser la raison !");
				return true;
			}

			Player target = Bukkit.getPlayerExact(args[0]);
			if (target == null || !target.isOnline()) {
				sender.sendMessage(PrefixMessage.PREFIX + "Le joueur " + args[0] + " n'a pas été trouvé");
				return true;
			}

			target.setHealth(0.0d);
			String raison = "";
			for (int i = 1, j = args.length; i < j; i++) {
				if (i + 1 < args.length) {
					raison += args[i] + " ";
				} else {
					raison += args[i];
				}
			}
			Bukkit.broadcastMessage(PrefixMessage.PREFIX + "Le joueur " + target.getDisplayName() + ChatColor.YELLOW
					+ " a été tué pour la raison §3\"" + raison + "\"§e !");
			return true;

		}
		return true;
	}

}