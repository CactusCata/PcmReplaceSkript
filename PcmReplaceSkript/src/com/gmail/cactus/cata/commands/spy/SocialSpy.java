package com.gmail.cactus.cata.commands.spy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class SocialSpy implements CommandExecutor {

	private CommandManager cmdManag;
	private MessageObj messageObj;

	public SocialSpy(CommandManager cmdManag, MessageObj messageObj) {
		this.cmdManag = cmdManag;
		this.messageObj = messageObj;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("socialspy") || cmd.getName().equalsIgnoreCase("spy")) {

			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			if (!(sender instanceof Player)) {
				sender.sendMessage(PrefixMessage.PREFIX_SENDER_BE_PLAYER + "");
				return true;
			}

			Player p = (Player) sender;
			if (messageObj.getSocial().contains(p.getName())) {
				p.sendMessage(PrefixMessage.PREFIX + "Vous ne voyez plus les messages des gens !");
				messageObj.getSocial().remove(p.getName());
				return true;
			}
			p.sendMessage(PrefixMessage.PREFIX + "Vous voyez les messages des gens !");
			messageObj.getSocial().add(p.getName());
			return true;

		}

		return false;
	}

}
