package com.gmail.cactus.cata.commands.other;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class Spawn implements CommandExecutor {

	private CommandManager cmdManag;

	public Spawn(CommandManager cmdManag) {
		this.cmdManag = cmdManag;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("spawn")) {

			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			if (!(sender instanceof Player)) {
				sender.sendMessage(PrefixMessage.PREFIX_SENDER_BE_PLAYER + "");
				return true;
			}

			Player player = (Player) sender;
			player.teleport(new Location(player.getWorld(), -0.5d, 65.2d, -0.5d, 0.0f, 0.0f));
			player.sendMessage(PrefixMessage.PREFIX + "Vous avez été téléporté au spawn !");
			return true;

		}
		return false;
	}
}
