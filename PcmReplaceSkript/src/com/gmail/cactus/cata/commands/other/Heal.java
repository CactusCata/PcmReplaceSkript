package com.gmail.cactus.cata.commands.other;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class Heal implements CommandExecutor {

	private CommandManager cmdManag;

	public Heal(CommandManager cmdManag) {
		this.cmdManag = cmdManag;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("heal")) {

			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			if (!(sender instanceof Player)) {
				sender.sendMessage(PrefixMessage.PREFIX_SENDER_BE_PLAYER + "");
				return true;
			}

			Player player = (Player) sender;
			player.setHealth(player.getMaxHealth());
			player.setFoodLevel(20);
			player.updateInventory();
			player.sendMessage(PrefixMessage.PREFIX + "Vous avez été heal !");
			return true;

		}

		return false;
	}

}
