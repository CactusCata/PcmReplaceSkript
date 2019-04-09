package com.gmail.cactus.cata.commands.spy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class R implements CommandExecutor {

	private MessageObj messageObj;
	private CommandManager cmdManag;

	public R(CommandManager cmdManag, MessageObj messageObj) {
		this.cmdManag = cmdManag;
		this.messageObj = messageObj;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("r")) {

			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			if (!(sender instanceof Player)) {
				sender.sendMessage(PrefixMessage.PREFIX_SENDER_BE_PLAYER + "");
				return true;
			}

			if (args.length < 0) {
				sender.sendMessage(PrefixMessage.PREFIX + "Veillez préciser le message");
				return true;
			}

			String msg = "";
			for (int i = 0, j = args.length; i < j; i++) {
				msg += " " + args[i];
			}

			Player p = messageObj.getMsgPlayer().get(sender);
			Player playersender = (Player) sender;

			if (!(p.isOnline())) {

				playersender.sendMessage(PrefixMessage.PREFIX + "Le joueur " + p.getName() + " n'a pas été trouvé !");
				return true;

			}

			try {

				playersender
						.sendMessage("§7[" + playersender.getDisplayName() + "§7->" + p.getDisplayName() + "§7]" + msg);
				p.sendMessage("§7[" + playersender.getDisplayName() + "§7->" + p.getDisplayName() + "§7]" + msg);

			} catch (java.lang.NullPointerException e) {
				sender.sendMessage(PrefixMessage.PREFIX + "Veillez d'abord parler à quelqu'un !");
				return true;
			}

			for (int i = 0, j = messageObj.getSocial().size(); i < j; i++) {
				if (!(messageObj.getSocial().get(i) == playersender.getName()
						|| messageObj.getSocial().get(i) == messageObj.getMsgPlayer().get(sender).getName())) {

					Player player = Bukkit.getServer().getPlayer(messageObj.getSocial().get(i));
					player.sendMessage(PrefixMessage.PREFIX_SPY + "§7[" + playersender.getDisplayName() + "§7->"
							+ p.getDisplayName() + "§7]" + msg);
				}
			}

			messageObj.getMsgPlayer().put(p, playersender);

			return true;

		}

		return false;
	}

}
