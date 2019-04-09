package com.gmail.cactus.cata.commands.other;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class Commands implements CommandExecutor {

	private CommandManager cmdmanag;

	public Commands(CommandManager cmdmanag) {
		this.cmdmanag = cmdmanag;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("commands")) {

			if (sender instanceof BlockCommandSender) {
				sender.sendMessage(PrefixMessage.PREFIX + "La commande ne peut pas être executé par commandblock !");
				return true;
			}

			if (sender instanceof Player) {
				Player player = (Player) sender;
				if ((!player.getName().equalsIgnoreCase("CactusCata"))
						&& (!player.getName().equalsIgnoreCase("Tazmaik"))) {
					player.sendMessage(PrefixMessage.PREFIX
							+ "Il n'y a que Tazmaik ou CactusCata qui puisse executer cette commande !");
					return true;
				}
			}

			if (args.length == 0) {

				sender.sendMessage(PrefixMessage.PREFIX + "Il faut préciser la commande !");
				return true;

			}

			if (!cmdmanag.getMap().containsKey(args[0])) {

				sender.sendMessage(PrefixMessage.PREFIX + "La commande " + args[0] + " n'a pas été trouvé !");
				return true;

			}

			boolean b = !cmdmanag.getMap().get(args[0]);
			cmdmanag.getMap().put(args[0], b);
			sender.sendMessage(PrefixMessage.PREFIX + "La commande " + args[0] + " a  été mise à " + b + " !");
			return true;

		}

		return false;
	}

}
