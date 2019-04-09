package com.gmail.cactus.cata.commands.tp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class Tppos implements CommandExecutor {
	
	private CommandManager cmdManag;
	
	public Tppos(CommandManager cmdManag) {
		this.cmdManag = cmdManag;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tppos")) {
			
			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			if (!(sender instanceof Player || sender instanceof BlockCommandSender)) {
				sender.sendMessage(PrefixMessage.PREFIX_SENDER_BE_PLAYER + "");
				return true;
			}

			if (args.length < 4) {
				sender.sendMessage(PrefixMessage.PREFIX + "Il manque des arguments !");
				return true;
			}

			try {
				@SuppressWarnings("unused")
				int i = (int) (Float.parseFloat(args[1] + args[2] + args[3]) / 10);
			} catch (java.lang.NumberFormatException e) {
				sender.sendMessage(PrefixMessage.PREFIX
						+ "La commande doit s'effectuer sous la forme: §a/tppos [joueur] [nombre] [nombre] [nombre] §b!");
				return true;
			}

			Player player = Bukkit.getPlayerExact(args[0]);
			if (player == null || !player.isOnline()) {
				sender.sendMessage(PrefixMessage.PREFIX + "Le joueur " + args[0] + " n'a pas été trouvé");
				return true;

			}

			float x = Float.parseFloat(args[1]);
			float y = Float.parseFloat(args[2]);
			float z = Float.parseFloat(args[3]);
			player.teleport(new Location(player.getWorld(), x, y, z));
			player.sendMessage(PrefixMessage.PREFIX + "Vous avez été téléporté aux coordonées : x: " + x + " y: " + y
					+ " z: " + z);
			return true;

		}
		return false;
	}

}
