package com.gmail.cactus.cata.commands.other;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class Broadcast implements CommandExecutor {

	private CommandManager cmdManag;

	public Broadcast(CommandManager cmdManag) {
		this.cmdManag = cmdManag;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("broadcast") || cmd.getName().equalsIgnoreCase("bcast")) {

			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			if (args.length == 0) {
				sender.sendMessage(PrefixMessage.PREFIX + "Veillez préciser le message !");
				return true;
			}

			String broadcast = "";

			for (int i = 0; i < args.length; i++) {
				if (i != 0)
					broadcast += " ";
				broadcast += args[i];
			}

			Bukkit.broadcastMessage(broadcast.replace('&', '§'));

			return true;

		}
		return false;
	}
}
