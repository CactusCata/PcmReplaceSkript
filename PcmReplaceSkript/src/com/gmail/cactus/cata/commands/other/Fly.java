package com.gmail.cactus.cata.commands.other;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class Fly implements CommandExecutor {

	private ArrayList<UUID> flying = new ArrayList<>();
	private CommandManager cmdManag;
	
	public Fly(CommandManager cmdManag) {
		this.cmdManag = cmdManag;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (cmd.getName().equalsIgnoreCase("fly")) {
			
			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			if (!(sender instanceof Player)) {
				sender.sendMessage(PrefixMessage.PREFIX_SENDER_BE_PLAYER + "");
				return true;
			}

			Player player = (Player) sender;
			
			if(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR){
				player.sendMessage(PrefixMessage.PREFIX + "Vous pouvez déjà voler !");
				return true;
			}
			
			if (flying.contains(player.getUniqueId())) {
				player.sendMessage(PrefixMessage.PREFIX + "Vous ne volez plus !");
				player.setAllowFlight(false);
				flying.remove(player.getUniqueId());
				return true;
			} else {
				player.sendMessage(PrefixMessage.PREFIX + "" + ChatColor.ITALIC + "i believe i can fly !");
				player.setAllowFlight(true);
				flying.add(player.getUniqueId());
				return true;
			}

		}
		return false;

	}
}
