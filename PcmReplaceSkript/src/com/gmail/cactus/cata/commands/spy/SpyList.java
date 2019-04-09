package com.gmail.cactus.cata.commands.spy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class SpyList implements CommandExecutor {

	private CommandManager cmdManag;
	private MessageObj messageObj;

	public SpyList(CommandManager cmdManag, MessageObj messageObj) {
		this.cmdManag = cmdManag;
		this.messageObj = messageObj;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spylist")) {

			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			if (messageObj.getSocial().isEmpty()) {
				sender.sendMessage(PrefixMessage.PREFIX + "Personne n'a le spy d'activé !");
				return true;
			}

			String spyMessage = PrefixMessage.PREFIX + "Les personnes qui ont le /spy d'activé sont :";

			for (int i = 0, j = messageObj.getSocial().size(); i < j; i++)
				spyMessage += "\n" + messageObj.getSocial().get(i);

			sender.sendMessage(spyMessage);
			return true;

		}
		return false;
	}

}
