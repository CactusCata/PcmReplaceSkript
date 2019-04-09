package com.gmail.cactus.cata.commands.tp;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;
import com.gmail.cactus.cata.vanish.VanishManager;

public class Tpb implements CommandExecutor {

	private CommandManager cmdManag;
	private VanishManager vanishManag;

	public Tpb(CommandManager cmdManag, VanishManager vanishManag) {
		this.cmdManag = cmdManag;
		this.vanishManag = vanishManag;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tpb")) {

			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			if (args.length < 2) {
				sender.sendMessage(
						PrefixMessage.PREFIX + "Veillez préciser quel joueur doit être téléporté à quel joueur !");
				return true;
			}

			Player player1 = Bukkit.getPlayerExact(args[0]);
			Player player2 = Bukkit.getPlayerExact(args[1]);
			if (player1 == null || !player1.isOnline()) {
				sender.sendMessage(PrefixMessage.PREFIX + "Le joueur " + args[0] + " n'a pas été trouvé");
				return true;
			}
			if (player2 == null || !player2.isOnline()) {
				sender.sendMessage(PrefixMessage.PREFIX + "Le joueur " + args[1] + " n'a pas été trouvé");
				return true;
			}

			player1.teleport(player2);
			Player playersender = (Player) sender;

			if (!vanishManag.getList().contains(playersender)) {
				player1.sendMessage(PrefixMessage.PREFIX + "Vous avez été téléporté sur " + player2.getDisplayName()
						+ " par " + playersender.getDisplayName() + " !");
				player2.sendMessage(PrefixMessage.PREFIX + player1.getDisplayName() + " a été téléporté sur vous par "
						+ playersender.getDisplayName() + " !");
			}

			sender.sendMessage(PrefixMessage.PREFIX + player1.getDisplayName() + " a été téléporté sur "
					+ player2.getDisplayName() + " !");

			return true;

		}
		return false;
	}

}
