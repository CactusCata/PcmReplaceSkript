package com.gmail.cactus.cata.commands.other;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.GameObj;
import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class Gamemodeb implements CommandExecutor {

	private CommandManager cmdManag;
	private GameObj gObj;

	public Gamemodeb(CommandManager cmdManag, GameObj gObj) {
		this.cmdManag = cmdManag;
		this.gObj = gObj;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("gamemodeb")) {

			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			if (!(sender instanceof Player)) {
				sender.sendMessage(PrefixMessage.PREFIX_SENDER_BE_PLAYER + "");
				return true;
			}

			Player player = (Player) sender;
			if (gObj.getGamemodeB().contains(player)) {
				player.sendMessage(PrefixMessage.PREFIX + "Vous serez visé par les mini-jeux (adventure) !");
				player.setGameMode(GameMode.ADVENTURE);
				gObj.getGamemodeB().remove(player);
				return true;
			} else {
				player.sendMessage(PrefixMessage.PREFIX + "Vous ne serez plus visé par les mini-jeux (survival) !");
				player.setGameMode(GameMode.SURVIVAL);
				gObj.getGamemodeB().add(player);
				return true;
			}

		}
		return false;
	}

}
